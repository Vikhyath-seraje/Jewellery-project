package com.goldshop.controller;

import com.goldshop.model.GoldRateHistory;
import com.goldshop.repository.GoldRateHistoryRepository;
import com.goldshop.service.GoldRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gold-rate")
@CrossOrigin(origins = "*")
public class GoldRateController {

    @Autowired
    private GoldRateService goldRateService;

    @Autowired
    private GoldRateHistoryRepository goldRateHistoryRepository;

    @GetMapping("/today")
    public ResponseEntity<?> getTodayRate(@RequestParam(required = false) String metalType,
            @RequestParam(required = false) String purity) {
        if (metalType != null && purity != null) {
            Double rate = goldRateService.getTodayRate(metalType, purity);
            if (rate == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(rate);
        }
        // Default to Gold 22K if not specified (backward compatibility)
        Double rate = goldRateService.getTodayRate();
        if (rate == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rate);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> manualUpdate(@RequestParam Double rate,
            @RequestParam(defaultValue = "GOLD") String metalType,
            @RequestParam(defaultValue = "22K") String purity) {
        goldRateService.manualUpdate(rate, metalType, purity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fetch")
    public ResponseEntity<Void> forceFetch() {
        goldRateService.updateDailyGoldRate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public List<GoldRateHistory> getHistory() {
        return goldRateHistoryRepository.findAll();
    }
}
