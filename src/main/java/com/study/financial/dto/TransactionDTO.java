package com.study.financial.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.study.financial.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    @JsonUnwrapped
    private Transaction transaction;

    private String categoryName;
}
