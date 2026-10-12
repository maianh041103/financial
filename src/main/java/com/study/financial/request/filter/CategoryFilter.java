package com.study.financial.request.filter;

import com.study.financial.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryFilter {
    private String keyword;

    private Long userId;

    private TransactionType categoryType;
}
