package com.study.financial.request;

import com.study.financial.enums.TransactionType;
import com.study.financial.messages.CategoryMessage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotNull(message = CategoryMessage.CATEGORY_NAME_NOT_NULL)
    private String name;
    private String color;
    private String avatar;
    private String description;
    private TransactionType type = TransactionType.INCOME;
}
