package com.study.financial.request;

import com.study.financial.enums.TransactionType;
import com.study.financial.messages.CategoryMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Không dùng @AllArgsConstructor: Jackson 3 (Boot 4) ưu tiên canonical constructor
 * khi deserialize và sẽ truyền null cho field vắng mặt, làm mất mặc định type=INCOME.
 */
@Data
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank(message = CategoryMessage.CATEGORY_NAME_NOT_NULL)
    private String name;
    private String color;
    private String avatar;
    private String description;
    private TransactionType type = TransactionType.INCOME;
}
