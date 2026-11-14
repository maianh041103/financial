package com.study.financial.request;

import com.study.financial.enums.PaymentMethod;
import com.study.financial.enums.TransactionType;
import com.study.financial.messages.TransactionMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Không dùng @Builder/@AllArgsConstructor: Jackson 3 (Boot 4) ưu tiên canonical constructor
 * khi deserialize và sẽ truyền null cho field vắng mặt, làm mất giá trị mặc định của paymentMethod.
 */
@Data
@NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = TransactionMessage.TRANSACTION_TYPE_NOT_NULL)
    private TransactionType transactionType;

    @NotNull(message = TransactionMessage.CATEGORY_NOT_NULL)
    private Long categoryId;

    @NotBlank(message = TransactionMessage.NAME_NOT_NULL)
    private String name;

    @NotNull(message = TransactionMessage.AMOUNT_NOT_NULL)
    @Positive(message = TransactionMessage.AMOUNT_MIN)
    private BigDecimal amount;

    @NotNull(message = TransactionMessage.TRANSACTION_DATE_NOT_NULL)
    private LocalDate transactionDate;

    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    private String note;
}
