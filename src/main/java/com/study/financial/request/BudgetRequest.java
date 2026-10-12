package com.study.financial.request;

import com.study.financial.messages.BudgetMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetRequest {
    @NotNull(message = BudgetMessage.BUDGET_CATEGORY_NOT_NULL)
    private List<Long> categoryIds;

    @NotNull(message = BudgetMessage.BUDGET_NAME_NOT_NULL)
    private String name;

    @NotNull(message = BudgetMessage.BUDGET_AMOUNT_MIN)
    @Min(value = 0, message = BudgetMessage.BUDGET_AMOUNT_MIN)
    private BigDecimal amount;

    @NotNull(message = BudgetMessage.BUDGET_START_DATE_NOT_NULL)
    private LocalDate startDate;

    @NotNull(message = BudgetMessage.BUDGET_END_DATE_NOT_NULL)
    private LocalDate endDate;

    @NotNull(message = BudgetMessage.BUDGET_WARNING_PERCENT_NOT_NULL)
    @Min(value = 0, message = BudgetMessage.BUDGET_WARNING_PERCENT_MIN)
    private BigDecimal warningPercent = new BigDecimal("80.00");
}
