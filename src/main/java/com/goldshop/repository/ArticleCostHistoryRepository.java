package com.goldshop.repository;

import com.goldshop.model.ArticleCostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleCostHistoryRepository extends JpaRepository<ArticleCostHistory, Long> {
    List<ArticleCostHistory> findByDate(LocalDate date);
    Optional<ArticleCostHistory> findByArticleIdAndDate(Long articleId, LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM ArticleCostHistory ach WHERE ach.article.id = :articleId")
    void deleteByArticleId(Long articleId);
}
