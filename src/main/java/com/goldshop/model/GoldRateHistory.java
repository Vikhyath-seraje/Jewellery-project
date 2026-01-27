package com.goldshop.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "gold_rate_history")
public class GoldRateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "rate_per_gram", nullable = false)
    private Double ratePerGram;

    @Column(name = "metal_type")
    private String metalType; // e.g., "GOLD", "SILVER"

    @Column(name = "purity")
    private String purity; // e.g., "24K", "22K", "FINE"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getRatePerGram() {
        return ratePerGram;
    }

    public void setRatePerGram(Double ratePerGram) {
        this.ratePerGram = ratePerGram;
    }

    public String getMetalType() {
        return metalType;
    }

    public void setMetalType(String metalType) {
        this.metalType = metalType;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }
}
