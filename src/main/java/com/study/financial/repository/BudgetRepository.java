package com.study.financial.repository;

import com.study.financial.dto.BudgetDTO;
import com.study.financial.dto.ReportBudget;
import com.study.financial.entity.Budget;
import com.study.financial.request.filter.BudgetFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("""
        SELECT COUNT(b) > 0
        FROM Budget b
        JOIN BudgetCategory bc ON bc.budgetId = b.id
        WHERE bc.categoryId IN :categoryId
          AND NOT (b.endDate < :startDate OR b.startDate > :endDate)
    """)
    boolean existsByCategoryIdsInTime(List<Long> categoryIds, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(b) > 0 FROM Budget b WHERE b.name = :name " +
            "AND b.userId = :userId " +
            "AND NOT (b.endDate < :startDate OR b.startDate > :endDate)")
    boolean existsByNameInTime(String name, Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT b FROM Budget b WHERE id = :id AND userId = :userId")
    Optional<Budget> findByIdAndUserId(Long id, Long userId);

    @Query("""
        SELECT new com.study.financial.dto.BudgetDTO(
            b,
            COALESCE(SUM(t.amount), 0)
        )
        FROM Budget b
        JOIN BudgetCategory bc ON bc.budgetId = b.id
        LEFT JOIN Transaction t
            ON t.categoryId = bc.categoryId
            AND t.transactionDate >= b.startDate
            AND t.transactionDate <= b.endDate
        WHERE b.userId = :#{#filter.userId}
          AND (
              :#{#filter.keyword} IS NULL
              OR b.name LIKE %:#{#filter.keyword}%
          )
          AND (
              :#{#filter.categoryId} IS NULL
              OR bc.categoryId = :#{#filter.categoryId}
          )
          AND (
              :#{#filter.isActive} IS NULL
              OR (
                  :#{#filter.isActive} = true
                  AND b.startDate <= CURRENT_DATE
                  AND b.endDate >= CURRENT_DATE
              )
              OR (
                  :#{#filter.isActive} = false
                  AND (
                      b.startDate > CURRENT_DATE
                      OR b.endDate < CURRENT_DATE
                  )
              )
          )
          AND (
              :#{#filter.startDate} IS NULL
              OR b.startDate >= :#{#filter.startDate}
          )
          AND (
              :#{#filter.endDate} IS NULL
              OR b.endDate <= :#{#filter.endDate}
          )
        GROUP BY b.id
    """)
    Page<BudgetDTO> filter(BudgetFilter filter, Pageable pageable);

    @Query("""
        SELECT new com.study.financial.dto.ReportBudget(
            b.id,
            b.name,
            COALESCE(SUM(t.amount), 0),
            b.amount,
            b.warningPercent
        )
        FROM Budget b
        JOIN BudgetCategory bc ON bc.budgetId = b.id
        LEFT JOIN Transaction t
            ON t.categoryId = bc.categoryId
            AND t.transactionDate >= b.startDate
            AND t.transactionDate <= b.endDate
        WHERE b.userId = :userId
          AND  b.startDate <= :endDate
          AND b.endDate >= :startDate
        GROUP BY b.id
    """)
    List<ReportBudget> getReportBudget(LocalDate startDate, LocalDate endDate, Long userId);
}
