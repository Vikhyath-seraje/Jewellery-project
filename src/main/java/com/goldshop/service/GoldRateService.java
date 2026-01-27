package com.goldshop.service;

import com.goldshop.model.GoldRateHistory;
import com.goldshop.repository.GoldRateHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
public class GoldRateService {

    @Autowired
    private GoldRateHistoryRepository goldRateHistoryRepository;

    public void fetchGoldRateFromApi() {
        try {
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://gold.g.apised.com/v1/latest?metals=XAU,XAG&base_currency=INR&currencies=INR&weight_unit=gram")
                    .get()
                    .addHeader("x-api-key", "sk_4895F8e7a18178b8254Cd3E9a1dCb00c5c9A25FFf27cDDd3")
                    .build();

            try (okhttp3.Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("API call failed: " + response);
                    return;
                }

                String responseBody = response.body().string();
                System.out.println("API Response: " + responseBody);

                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(responseBody);
                com.fasterxml.jackson.databind.JsonNode metalPricesNode = root.path("data").path("metal_prices");

                LocalDate today = LocalDate.now();

                // Process Gold (XAU)
                com.fasterxml.jackson.databind.JsonNode xauNode = metalPricesNode.path("XAU");
                if (!xauNode.isMissingNode()) {
                    double goldRate24k = xauNode.path("price").asDouble();
                    saveRate(today, goldRate24k, "GOLD", "24K");
                    saveRate(today, goldRate24k * 0.916, "GOLD", "22K"); // Approx 22K rate
                }

                // Process Silver (XAG)
                com.fasterxml.jackson.databind.JsonNode xagNode = metalPricesNode.path("XAG");
                if (!xagNode.isMissingNode()) {
                    double silverRate = xagNode.path("price").asDouble();
                    saveRate(today, silverRate, "SILVER", "FINE");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveRate(LocalDate date, Double rate, String metalType, String purity) {
        Optional<GoldRateHistory> existing = goldRateHistoryRepository.findAll().stream()
                .filter(h -> h.getDate().equals(date) && h.getMetalType().equals(metalType)
                        && h.getPurity().equals(purity))
                .findFirst();

        if (existing.isPresent()) {
            return;
        }

        GoldRateHistory history = new GoldRateHistory();
        history.setDate(date);
        history.setRatePerGram(rate);
        history.setMetalType(metalType);
        history.setPurity(purity);
        goldRateHistoryRepository.save(history);
        System.out.println("Saved rate: " + metalType + " " + purity + " - " + rate);
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void updateDailyGoldRate() {
        fetchGoldRateFromApi();
    }

    public void manualUpdate(Double rate, String metalType, String purity) {
        LocalDate today = LocalDate.now();
        Optional<GoldRateHistory> existing = goldRateHistoryRepository.findAll().stream()
                .filter(h -> h.getDate().equals(today) && h.getMetalType().equals(metalType)
                        && h.getPurity().equals(purity))
                .findFirst();

        GoldRateHistory history = existing.orElse(new GoldRateHistory());
        history.setDate(today);
        history.setRatePerGram(rate);
        history.setMetalType(metalType);
        history.setPurity(purity);
        goldRateHistoryRepository.save(history);
    }

    public Double getTodayRate(String metalType, String purity) {
        return goldRateHistoryRepository.findAll().stream()
                .filter(h -> h.getDate().equals(LocalDate.now()) && h.getMetalType().equals(metalType)
                        && h.getPurity().equals(purity))
                .map(GoldRateHistory::getRatePerGram)
                .findFirst()
                .orElse(null);
    }

    // Fallback for existing calls - defaults to Gold 22K
    public Double getTodayRate() {
        return getTodayRate("GOLD", "22K");
    }
}
