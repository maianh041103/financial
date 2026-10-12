package com.study.financial.controller;

import com.study.financial.enums.TransactionType;
import com.study.financial.messages.CategoryMessage;
import com.study.financial.request.CategoryRequest;
import com.study.financial.request.filter.CategoryFilter;
import com.study.financial.response.ApiResponse;
import com.study.financial.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> create(
            @Valid @RequestBody CategoryRequest request
            ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                categoryService.create(request),
                CategoryMessage.CREATE_CATEGORY_SUCCESS
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                categoryService.update(id, request),
                CategoryMessage.UPDATE_CATEGORY_SUCCESS
        ));
    }

    @GetMapping("")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(value = "0") int page,
            @RequestParam(value = "10") int limit
    ){
        CategoryFilter filter = CategoryFilter.builder()
                .keyword(keyword)
                .categoryType(type)
                .build();
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                categoryService.filter(filter, page, limit),
                CategoryMessage.GET_ALL_CATEGORY_SUCCESS
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                categoryService.findById(id),
                CategoryMessage.GET_DETAIL_CATEGORY_SUCCESS
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ){
        categoryService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                null,
                CategoryMessage.DELETE_SUCCESS
        ));
    }
}
