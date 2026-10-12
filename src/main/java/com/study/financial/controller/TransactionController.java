package com.study.financial.controller;

import com.study.financial.enums.PaymentMethod;
import com.study.financial.enums.TransactionType;
import com.study.financial.messages.TransactionMessage;
import com.study.financial.request.TransactionRequest;
import com.study.financial.request.filter.TransactionFilter;
import com.study.financial.response.ApiResponse;
import com.study.financial.service.TransactionService;
import com.study.financial.utils.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody TransactionRequest request){
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        transactionService.create(request),
                        TransactionMessage.CREATE_EXPENSE_SUCCESS)
        );
    }

    @GetMapping("")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) TransactionType transactionType,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) PaymentMethod paymentMethod,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int page
            ){
        TransactionFilter filter = TransactionFilter.builder()
                .transactionType(transactionType)
                .categoryId(categoryId)
                .keyword(StringUtils.isBlank(keyword) ? null : keyword)
                .minAmount(minAmount)
                .maxAmount(maxAmount)
                .fromDate(fromDate)
                .toDate(toDate)
                .maxAmount(maxAmount)
                .minAmount(minAmount)
                .paymentMethod(paymentMethod)
                .build();

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        transactionService.filter(filter, page, limit),
                        TransactionMessage.GET_ALL_SUCCESS)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        transactionService.findById(id),
                        TransactionMessage.GET_DETAIL_SUCCESS
                        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                transactionService.update(id, request),
                TransactionMessage.UPDATE_TRANSACTION_SUCCESS
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ){
        transactionService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                null,
                TransactionMessage.DELETE_TRANSACTION_SUCCESS
        ));
    }
}
