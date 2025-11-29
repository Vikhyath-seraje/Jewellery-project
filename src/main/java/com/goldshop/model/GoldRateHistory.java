package com.goldshop.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "gold_rate_history")
public class GoldRateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Column(name = "rate_per_gram", nullable = false)
    private Double ratePerGram;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getRatePerGram() { return ratePerGram; }
    public void setRatePerGram(Double ratePerGram) { this.ratePerGram = ratePerGram; }
}
