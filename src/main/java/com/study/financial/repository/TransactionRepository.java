package com.study.financial.repository;

import com.study.financial.dto.ReportTransactionDetail;
import com.study.financial.dto.TransactionDTO;
import com.study.financial.dto.ReportTransaction;
import com.study.financial.entity.Transaction;
import com.study.financial.enums.GroupType;
import com.study.financial.request.filter.TransactionFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT new com.study.financial.dto.TransactionDTO(t, c.name) FROM Transaction t " +
            "LEFT JOIN Category c ON t.categoryId = c.id AND c.deletedAt IS NULL " +
            "WHERE t.userId = :#{#filter.userId} " +
            "AND (:#{#filter.transactionType} IS NULL OR t.transactionType = :#{#filter.transactionType}) " +
            "AND (:#{#filter.categoryId} IS NULL OR t.categoryId = :#{#filter.categoryId}) " +
            "AND (:#{#filter.keyword} IS NULL OR t.name LIKE %:#{#filter.keyword}%) " +
            "AND (:#{#filter.minAmount} IS NULL OR t.amount >= :#{#filter.minAmount}) " +
            "AND (:#{#filter.maxAmount} IS NULL OR t.amount <= :#{#filter.maxAmount}) " +
            "AND (:#{#filter.fromDate} IS NULL OR t.transactionDate >= :#{#filter.fromDate}) " +
            "AND (:#{#filter.toDate} IS NULL OR t.transactionDate <= :#{#filter.toDate}) " +
            "AND (:#{#filter.paymentMethod} IS NULL OR t.paymentMethod = :#{#filter.paymentMethod}) " +
            "AND t.deletedAt IS NULL")
    Page<TransactionDTO> filter(TransactionFilter filter, Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.id = :id " +
            "AND t.userId = :userId " +
            "AND t.deletedAt IS NULL")
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.categoryId IN :categoryIds " +
            "AND t.userId = :userId " +
            "AND t.deletedAt IS NULL " +
            "AND t.transactionDate >= :startDate " +
            "AND t.transactionDate <= :endDate")
    List<Transaction> findByCategoryIdInAndInTime(List<Long> categoryIds, Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType = 'INCOME' THEN t.amount ELSE -t.amount END), 0) " +
            "FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "AND t.deletedAt IS NULL")
    BigDecimal sum(Long userId);

    @Query("SELECT new com.study.financial.dto.ReportTransaction(" +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'INCOME' THEN t.amount ELSE 0 END), 0), " +
            "COALESCE(SUM(CASE WHEN t.transactionType = 'EXPENSE' THEN t.amount ELSE 0 END), 0)) " +
            "FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "AND t.deletedAt IS NULL " +
            "AND t.transactionDate >= :startDate " +
            "AND t.transactionDate <= :endDate")
    ReportTransaction getTransactionSummary(
            LocalDate startDate,
            LocalDate endDate,
            Long userId
    );

    @Query(value = """
    SELECT
        CASE
            WHEN :groupType = 'DAY'
                THEN DATE_FORMAT(t.transaction_date, '%Y-%m-%d')
            WHEN :groupType = 'WEEK'
                THEN DATE_FORMAT(t.transaction_date, '%x-%v')
            WHEN :groupType = 'MONTH'
                THEN DATE_FORMAT(t.transaction_date, '%Y-%m')
            WHEN :groupType = 'YEAR'
                THEN DATE_FORMAT(t.transaction_date, '%Y')
        END AS groupDate,
        SUM(CASE
            WHEN t.transaction_type = 'INCOME' THEN t.amount
            ELSE 0
        END) AS totalIncome
    FROM transactions t
    WHERE t.user_id = :userId
      AND t.deleted_at IS NULL
      AND t.transaction_date >= :startDate
      AND t.transaction_date <= :endDate
    GROUP BY groupDate
    ORDER BY groupDate
    """, nativeQuery = true)
    List<Object[]> getTransactionDetail(
            LocalDate startDate,
            LocalDate endDate,
            Long userId,
            String groupType
    );


}
