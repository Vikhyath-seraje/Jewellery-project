package com.goldshop.repository;

import com.goldshop.model.ArticleCostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleCostHistoryRepository extends JpaRepository<ArticleCostHistory, Long> {
    List<ArticleCostHistory> findByDate(LocalDate date);
    Optional<ArticleCostHistory> findByArticleIdAndDate(Long articleId, LocalDate date);
}
