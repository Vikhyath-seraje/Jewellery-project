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
    public ResponseEntity<Double> getTodayRate() {
        Double rate = goldRateService.getTodayRate();
        if (rate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rate);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> manualUpdate(@RequestParam Double rate) {
        goldRateService.manualUpdate(rate);
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
