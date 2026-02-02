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

    public List<Article> getArticlesByCategory(String category) {
        return articleRepository.findByCategory(category);
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public void deleteArticle(Long id) {
        articleCostHistoryRepository.deleteByArticleId(id);
        articleRepository.deleteById(id);
    }

    public void deleteAllArticles() {
        articleCostHistoryRepository.deleteAll();
        articleRepository.deleteAll();
    }

    public ArticleCostHistory calculateCost(Article article, Double goldRate) {
        double manufacturingCost = (article.getWeightGrams() * goldRate)
                + article.getMakingCharges()
                + (article.getWeightGrams() * goldRate * article.getWastagePercentage() / 100);

        double sellingPrice = (article.getWeightGrams() * goldRate)
                + article.getMakingCharges()
                + (article.getWeightGrams() * goldRate * article.getWastagePercentage() / 100);

        ArticleCostHistory costHistory = new ArticleCostHistory();
        costHistory.setArticle(article);
        costHistory.setDate(LocalDate.now());
        costHistory.setGoldRate(goldRate);
        costHistory.setCalculatedCost(manufacturingCost);
        costHistory.setSellingPrice(sellingPrice);

        return costHistory;
    }

    public void updateDailyPrices() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            String metalType = article.getMetalType();
            String purity = article.getPurity();
            Double rate = goldRateService.getTodayRate(metalType, purity);

            // Fallback to default if specific rate not found
            if (rate == null && "GOLD".equalsIgnoreCase(metalType)) {
                rate = goldRateService.getTodayRate();
            }

            if (rate != null) {
                if (articleCostHistoryRepository.findByArticleIdAndDate(article.getId(), LocalDate.now()).isEmpty()) {
                    ArticleCostHistory cost = calculateCost(article, rate);
                    articleCostHistoryRepository.save(cost);
                }
            }
        }
    }

    public List<ArticleCostHistory> getTodaySellingPrices() {
        updateDailyPrices();
        return articleCostHistoryRepository.findByDate(LocalDate.now());
    }
}
