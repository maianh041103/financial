package com.study.financial.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.study.financial.entity.Budget;
import com.study.financial.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDetailDTO {
    @JsonUnwrapped
    private Budget budget;

    private BigDecimal amountUsed;

    private HashMap<String, List<Transaction>> transactions;
}
