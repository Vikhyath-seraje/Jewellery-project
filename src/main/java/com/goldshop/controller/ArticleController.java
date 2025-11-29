package com.goldshop.controller;

import com.goldshop.model.Article;
import com.goldshop.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*") // Allow frontend access
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleService.saveArticle(article);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        return articleService.getArticleById(id)
                .map(article -> {
                    article.setArticleId(articleDetails.getArticleId());
                    article.setName(articleDetails.getName());
                    article.setDescription(articleDetails.getDescription());
                    article.setWeightGrams(articleDetails.getWeightGrams());
                    article.setMakingCharges(articleDetails.getMakingCharges());
                    article.setWastagePercentage(articleDetails.getWastagePercentage());
                    // manufacturedDate is usually fixed, but can be updated if needed
                    return ResponseEntity.ok(articleService.saveArticle(article));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
