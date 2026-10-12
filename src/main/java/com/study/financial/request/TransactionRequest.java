package com.study.financial.request;

import com.study.financial.enums.PaymentMethod;
import com.study.financial.enums.TransactionType;
import com.study.financial.messages.TransactionMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    @NotNull(message = TransactionMessage.TRANSACTION_TYPE_NOT_NULL)
    private TransactionType transactionType;

    @NotNull(message = TransactionMessage.CATEGORY_NOT_NULL)
    private Long categoryId;

    @NotNull(message = TransactionMessage.NAME_NOT_NULL)
    private String name;

    @NotNull(message = TransactionMessage.AMOUNT_NOT_NULL)
    @Min(value = 0, message = TransactionMessage.AMOUNT_MIN)
    private BigDecimal amount;

    @NotNull(message = TransactionMessage.TRANSACTION_DATE_NOT_NULL)
    private LocalDate transactionDate;

    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    private String note;
}
