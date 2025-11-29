package com.goldshop.controller;

import com.goldshop.model.ArticleCostHistory;
import com.goldshop.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costs")
@CrossOrigin(origins = "*")
public class CostController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/selling-prices")
    public List<ArticleCostHistory> getTodaySellingPrices() {
        return articleService.getTodaySellingPrices();
    }
}
