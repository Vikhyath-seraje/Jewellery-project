package com.goldshop.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id", unique = true, nullable = false)
    private String articleId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "weight_grams", nullable = false)
    private Double weightGrams;

    @Column(name = "making_charges", nullable = false)
    private Double makingCharges;

    @Column(name = "wastage_percentage", nullable = false)
    private Double wastagePercentage;

    @Column(name = "manufactured_date")
    private LocalDate manufacturedDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getArticleId() { return articleId; }
    public void setArticleId(String articleId) { this.articleId = articleId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getWeightGrams() { return weightGrams; }
    public void setWeightGrams(Double weightGrams) { this.weightGrams = weightGrams; }

    public Double getMakingCharges() { return makingCharges; }
    public void setMakingCharges(Double makingCharges) { this.makingCharges = makingCharges; }

    public Double getWastagePercentage() { return wastagePercentage; }
    public void setWastagePercentage(Double wastagePercentage) { this.wastagePercentage = wastagePercentage; }

    public LocalDate getManufacturedDate() { return manufacturedDate; }
    public void setManufacturedDate(LocalDate manufacturedDate) { this.manufacturedDate = manufacturedDate; }
}
