package com.study.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTransaction {
    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private List<ReportTransactionDetail> details;

    public ReportTransaction(BigDecimal totalIncome, BigDecimal totalExpense){
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }
}
