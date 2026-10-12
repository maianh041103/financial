package com.study.financial.request.filter;

import com.study.financial.enums.PaymentMethod;
import com.study.financial.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionFilter {
    private Long userId;

    private TransactionType transactionType;

    private Long categoryId;

    private String keyword;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private LocalDate fromDate;

    private LocalDate toDate;

    private PaymentMethod paymentMethod;
}
