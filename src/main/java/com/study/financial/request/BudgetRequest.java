package com.study.financial.request;

import com.study.financial.messages.BudgetMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Không dùng @AllArgsConstructor: Jackson 3 (Boot 4) ưu tiên canonical constructor
 * khi deserialize và sẽ truyền null cho field vắng mặt, làm mất mặc định warningPercent=80.
 */
@Data
@NoArgsConstructor
public class BudgetRequest {
    @NotNull(message = BudgetMessage.BUDGET_CATEGORY_NOT_NULL)
    private List<Long> categoryIds;

    @NotBlank(message = BudgetMessage.BUDGET_NAME_NOT_NULL)
    private String name;

    @NotNull(message = BudgetMessage.BUDGET_AMOUNT_NOT_NULL)
    @Positive(message = BudgetMessage.BUDGET_AMOUNT_MIN)
    private BigDecimal amount;

    @NotNull(message = BudgetMessage.BUDGET_START_DATE_NOT_NULL)
    private LocalDate startDate;

    @NotNull(message = BudgetMessage.BUDGET_END_DATE_NOT_NULL)
    private LocalDate endDate;

    @NotNull(message = BudgetMessage.BUDGET_WARNING_PERCENT_NOT_NULL)
    @Positive(message = BudgetMessage.BUDGET_WARNING_PERCENT_MIN)
    private BigDecimal warningPercent = new BigDecimal("80.00");
}
