package com.goldshop.repository;

import com.goldshop.model.GoldRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GoldRateHistoryRepository extends JpaRepository<GoldRateHistory, Long> {
    Optional<GoldRateHistory> findByDate(LocalDate date);
    Optional<GoldRateHistory> findTopByOrderByDateDesc();
}
