package com.goldshop.service;

import com.goldshop.model.Article;
import com.goldshop.model.ArticleCostHistory;
import com.goldshop.repository.ArticleCostHistoryRepository;
import com.goldshop.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCostHistoryRepository articleCostHistoryRepository;

    @Autowired
    private GoldRateService goldRateService;

    public Article saveArticle(Article article) {
        if (article.getManufacturedDate() == null) {
            article.setManufacturedDate(LocalDate.now());
        }
        return articleRepository.save(article);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public ArticleCostHistory calculateCost(Article article, Double goldRate) {
        double manufacturingCost = (article.getWeightGrams() * goldRate)
                + article.getMakingCharges()
                + (article.getWeightGrams() * goldRate * article.getWastagePercentage() / 100);

        double sellingPrice = (article.getWeightGrams() * goldRate)
                + article.getMakingCharges()
                + (article.getWeightGrams() * goldRate * article.getWastagePercentage() / 100);
        
        // Note: The formula for manufacturing cost and selling price in the requirements 
        // seems identical in structure but uses different rates (manufacture date rate vs current date rate).
        // Here we calculate based on the provided 'goldRate'.

        ArticleCostHistory costHistory = new ArticleCostHistory();
        costHistory.setArticle(article);
        costHistory.setDate(LocalDate.now());
        costHistory.setGoldRate(goldRate);
        costHistory.setCalculatedCost(manufacturingCost); // This might need adjustment if we strictly separate mfg cost from selling price logic
        costHistory.setSellingPrice(sellingPrice);
        
        return costHistory;
    }

    public void updateDailyPrices() {
        Double todayRate = goldRateService.getTodayRate();
        if (todayRate == null) {
            // Try to fetch or trigger update
            goldRateService.updateDailyGoldRate();
            todayRate = goldRateService.getTodayRate();
        }

        if (todayRate != null) {
            List<Article> articles = articleRepository.findAll();
            for (Article article : articles) {
                // Check if already calculated for today
                if (articleCostHistoryRepository.findByArticleIdAndDate(article.getId(), LocalDate.now()).isEmpty()) {
                    ArticleCostHistory cost = calculateCost(article, todayRate);
                    articleCostHistoryRepository.save(cost);
                }
            }
        }
    }
    
    public List<ArticleCostHistory> getTodaySellingPrices() {
        updateDailyPrices(); // Ensure they are calculated
        return articleCostHistoryRepository.findByDate(LocalDate.now());
    }
}
