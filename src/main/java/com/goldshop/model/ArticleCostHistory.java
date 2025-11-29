package com.goldshop.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "article_cost_history")
public class ArticleCostHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "gold_rate", nullable = false)
    private Double goldRate;

    @Column(name = "calculated_cost")
    private Double calculatedCost;

    @Column(name = "selling_price")
    private Double sellingPrice;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getGoldRate() { return goldRate; }
    public void setGoldRate(Double goldRate) { this.goldRate = goldRate; }

    public Double getCalculatedCost() { return calculatedCost; }
    public void setCalculatedCost(Double calculatedCost) { this.calculatedCost = calculatedCost; }

    public Double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }
}
