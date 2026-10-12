package com.study.financial.repository;

import com.study.financial.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
    @Query("SELECT bc FROM BudgetCategory bc WHERE budgetId = :budgetId")
    List<BudgetCategory> findByBudgetId(Long budgetId);

    @Transactional
    @Modifying
    void deleteByBudgetId(Long budgetId);
}
