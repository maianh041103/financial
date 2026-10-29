package com.study.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportBudget {
    private Long id;

    private String name;

    private BigDecimal amountUsed;

    private BigDecimal budgetAmount;

    private BigDecimal warningPercent;
}
