package com.study.financial.request.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetFilter {
    private String keyword;
    private Long categoryId;
    private Boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;
}
