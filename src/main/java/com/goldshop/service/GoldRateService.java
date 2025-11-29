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

    // Real API integration
    public Double fetchGoldRateFromApi() {
        try {
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://gold.g.apised.com/v1/latest?metals=XAU,XAG,XPT,XPD&base_currency=INR&currencies=INR&weight_unit=gram")
                    .get()
                    .addHeader("x-api-key", "sk_4895F8e7a18178b8254Cd3E9a1dCb00c5c9A25FFf27cDDd3")
                    .build();

            try (okhttp3.Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("API call failed: " + response);
                    return null;
                }
                
                String responseBody = response.body().string();
                System.out.println("API Response: " + responseBody);
                
                // Parse JSON
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(responseBody);
                
                // Navigate to the correct path: data.metal_prices.XAU.price
                // Response structure: {"status":"success","data":{"metal_prices":{"XAU":{"price":12110.34734,...}}}}
                com.fasterxml.jackson.databind.JsonNode dataNode = root.path("data");
                com.fasterxml.jackson.databind.JsonNode metalPricesNode = dataNode.path("metal_prices");
                com.fasterxml.jackson.databind.JsonNode xauNode = metalPricesNode.path("XAU");
                com.fasterxml.jackson.databind.JsonNode priceNode = xauNode.path("price");
                
                if (!priceNode.isMissingNode() && priceNode.isNumber()) {
                    double ratePerGram = priceNode.asDouble();
                    System.out.println("Extracted Gold Rate (INR per gram): " + ratePerGram);
                    return ratePerGram;
                }
                
                System.err.println("Failed to find price in expected path");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Scheduled(cron = "0 0 9 * * ?") // Runs every day at 9 AM
    public void updateDailyGoldRate() {
        LocalDate today = LocalDate.now();
        if (goldRateHistoryRepository.findByDate(today).isPresent()) {
            return; // Already updated for today
        }

        Double rate = fetchGoldRateFromApi();
        GoldRateHistory history = new GoldRateHistory();
        history.setDate(today);
        history.setRatePerGram(rate);
        goldRateHistoryRepository.save(history);
        System.out.println("Updated gold rate for " + today + ": " + rate);
    }

    public void manualUpdate(Double rate) {
        LocalDate today = LocalDate.now();
        GoldRateHistory history = goldRateHistoryRepository.findByDate(today)
                .orElse(new GoldRateHistory());
        history.setDate(today);
        history.setRatePerGram(rate);
        goldRateHistoryRepository.save(history);
    }

    public Double getTodayRate() {
        return goldRateHistoryRepository.findByDate(LocalDate.now())
                .map(GoldRateHistory::getRatePerGram)
                .orElse(null);
    }
}
