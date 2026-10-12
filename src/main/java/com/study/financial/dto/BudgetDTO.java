package com.study.financial.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.study.financial.entity.Budget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO {
    @JsonUnwrapped
    private Budget budget;

    private BigDecimal amountUsed;
}
