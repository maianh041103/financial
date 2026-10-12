package com.study.financial.controller;

import com.study.financial.messages.BudgetMessage;
import com.study.financial.request.BudgetRequest;
import com.study.financial.request.filter.BudgetFilter;
import com.study.financial.response.ApiResponse;
import com.study.financial.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping("")
    public ResponseEntity<?> create(
            @Valid @RequestBody BudgetRequest request
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                budgetService.create(request),
                BudgetMessage.CREATE_BUDGET_SUCCESS));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest request
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                budgetService.update(id, request),
                BudgetMessage.UPDATE_BUDGET_SUCCESS));
    }

    @GetMapping("")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false)LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(value = "0") int page,
            @RequestParam(value = "10") int limit
            ){
        BudgetFilter filter = BudgetFilter.builder()
                .keyword(keyword)
                .categoryId(categoryId)
                .isActive(isActive)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                budgetService.filter(filter, page, limit),
                BudgetMessage.GET_ALL_BUDGET_SUCCESS
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                budgetService.findById(id),
                BudgetMessage.GET_DETAIL_BUDGET_SUCCESS
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ){
        budgetService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                null,
                BudgetMessage.DELETE_BUDGET_SUCCESS
        ));
    }
}
