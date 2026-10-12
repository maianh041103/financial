package com.study.financial.repository;

import com.study.financial.dto.TransactionDTO;
import com.study.financial.entity.Transaction;
import com.study.financial.request.filter.TransactionFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT new com.study.financial.dto.TransactionDTO(t, c.name) FROM Transaction t " +
            "LEFT JOIN Category c ON t.categoryId = c.id " +
            "WHERE t.userId = :#{#filter.userId} " +
            "AND (:#{#filter.transactionType} IS NULL OR t.transactionType = :#{#filter.transactionType})" +
            "AND (:#{#filter.categoryId} IS NULL OR t.categoryId = :#{#filter.categoryId}) " +
            "AND (:#{#filter.keyword} IS NULL OR t.name LIKE %:#{#filter.keyword}%) " +
            "AND (:#{#filter.minAmount} IS NULL OR t.amount >= :#{#filter.minAmount}) " +
            "AND (:#{#filter.maxAmount} IS NULL OR t.amount <= :#{#filter.maxAmount}) " +
            "AND (:#{#filter.fromDate} IS NULL OR t.transactionDate >= :#{#filter.fromDate}) " +
            "AND (:#{#filter.toDate} IS NULL OR t.transactionDate <= :#{#filter.toDate}) " +
            "AND (:#{#filter.paymentMethod} IS NULL OR t.paymentMethod = :#{#filter.paymentMethod}) " +
            "AND t.deletedAt IS NULL")
    Page<TransactionDTO> filter(TransactionFilter filter, Pageable pageable);

    Optional<Transaction> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.id IN :categoryIds " +
            "AND t.transactionDate >= :startDate " +
            "AND t.transactionDate <= :endDate")
    List<Transaction> findByCategoryIdInAndInTime(List<Long> categoryIds, LocalDate startDate, LocalDate endDate);
}
