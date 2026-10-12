package com.study.financial.service;

import com.study.financial.dto.BudgetDTO;
import com.study.financial.dto.BudgetDetailDTO;
import com.study.financial.entity.Budget;
import com.study.financial.entity.BudgetCategory;
import com.study.financial.entity.Category;
import com.study.financial.entity.Transaction;
import com.study.financial.exception.BusinessException;
import com.study.financial.messages.BudgetMessage;
import com.study.financial.messages.CategoryMessage;
import com.study.financial.repository.BudgetCategoryRepository;
import com.study.financial.repository.BudgetRepository;
import com.study.financial.repository.CategoryRepository;
import com.study.financial.repository.TransactionRepository;
import com.study.financial.request.BudgetRequest;
import com.study.financial.request.filter.BudgetFilter;
import com.study.financial.utils.ListUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Budget create(
            @Valid @RequestBody BudgetRequest request
    ){
        request.setName(request.getName().trim());
        if(request.getEndDate().isBefore(request.getStartDate())){
            throw new BusinessException(BudgetMessage.END_DATE_CANNOT_BE_BEFORE_START_DATE, HttpStatus.BAD_REQUEST);
        }
        Long userId = JwtService.getUserId();
        List<Category> categories = categoryRepository.findByIdInAndUserId(request.getCategoryIds(), userId);
        if(categories.size() != request.getCategoryIds().size()){
            throw new BusinessException(CategoryMessage.CATEGORY_NOT_EXISTED, HttpStatus.BAD_REQUEST);
        }
        if(budgetRepository.existsByCategoryIdsInTime(request.getCategoryIds(), request.getStartDate(), request.getEndDate())){
            throw new BusinessException(BudgetMessage.BUDGET_EXISTED, HttpStatus.BAD_REQUEST);
        }
        if(budgetRepository.existsByNameInTime(request.getName(), userId, request.getStartDate(), request.getEndDate())){
            throw new BusinessException(BudgetMessage.BUDGET_NAME_EXISTED, HttpStatus.BAD_REQUEST);
        }

        Budget budget = budgetRepository.save(Budget.builder()
                        .userId(userId)
                        .name(request.getName())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .amount(request.getAmount())
                        .warningPercent(request.getWarningPercent())
                .build());

        List<BudgetCategory> budgetCategories = request.getCategoryIds().stream().map(x -> BudgetCategory.builder()
                        .budgetId(budget.getId())
                        .categoryId(x).build()).toList();
        budgetCategoryRepository.saveAll(budgetCategories);
        return budget;
    }

    @Transactional
    public Budget update(Long id, BudgetRequest request){
        if(request.getEndDate().isBefore(request.getStartDate())){
            throw new BusinessException(BudgetMessage.END_DATE_CANNOT_BE_BEFORE_START_DATE, HttpStatus.BAD_REQUEST);
        }
        Long userId = JwtService.getUserId();
        Budget budget = budgetRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException(BudgetMessage.BUDGET_NOT_EXISTED, HttpStatus.NOT_FOUND));

        List<BudgetCategory> budgetCategories = budgetCategoryRepository.findByBudgetId(budget.getId());
        if(budgetCategories.isEmpty()) throw new BusinessException(BudgetMessage.BUDGET_CATEGORY_NOT_EXISTED, HttpStatus.NOT_FOUND);
        List<Long> categoryIds = budgetCategories.stream().map(BudgetCategory::getCategoryId).toList();

        if(!ListUtils.isEquals(categoryIds, request.getCategoryIds())){
            List<Category> newCategories = categoryRepository.findByIdInAndUserId(request.getCategoryIds(), userId);
            if(newCategories.size() != request.getCategoryIds().size()) {
                throw new BusinessException(CategoryMessage.CATEGORY_NOT_EXISTED, HttpStatus.NOT_FOUND);
            }
            if(budgetRepository.existsByCategoryIdsInTime(request.getCategoryIds(), request.getStartDate(), request.getEndDate())){
                throw new BusinessException(BudgetMessage.BUDGET_CATEGORY_EXISTED, HttpStatus.BAD_REQUEST);
            }

            budgetCategoryRepository.deleteByBudgetId(id);


            List<BudgetCategory> newBudgetCategories = request.getCategoryIds().stream().map(x -> BudgetCategory.builder()
                    .budgetId(budget.getId())
                    .categoryId(x).build()).toList();
            budgetCategoryRepository.saveAll(newBudgetCategories);
        }

        request.setName(request.getName().trim());
        if(!budget.getName().equals(request.getName())){
            if(budgetRepository.existsByNameInTime(request.getName(), userId, request.getStartDate(), request.getEndDate())){
                throw new BusinessException(BudgetMessage.BUDGET_NAME_EXISTED, HttpStatus.BAD_REQUEST);
            }
            budget.setName(request.getName());
        }

        request.setStartDate(request.getStartDate());
        request.setEndDate(request.getEndDate());
        return budget;
    }

    public Page<BudgetDTO> filter(BudgetFilter filter, int page, int limit){
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        if(Boolean.FALSE.equals(filter.getIsActive())) filter.setIsActive(null);
        return budgetRepository.filter(filter, pageable);
    }

    public BudgetDetailDTO findById(Long id){
        Long userId = JwtService.getUserId();
        Budget budget = budgetRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException(BudgetMessage.BUDGET_NOT_EXISTED, HttpStatus.NOT_FOUND));

        List<BudgetCategory> budgetCategories = budgetCategoryRepository.findByBudgetId(id);
        if(budgetCategories.isEmpty())
            return new BudgetDetailDTO(budget, BigDecimal.ZERO, null);
        List<Long> categoryIds = budgetCategories.stream().map(BudgetCategory::getCategoryId).toList();
        List<Category> categories = categoryRepository.findByIdInAndUserId(categoryIds, userId);
        List<Transaction> transactions = transactionRepository.findByCategoryIdInAndInTime(categoryIds, budget.getStartDate(), budget.getEndDate());

        BigDecimal usedAmount = BigDecimal.ZERO;
        for(Transaction transaction : transactions) usedAmount = usedAmount.add(transaction.getAmount());
        HashMap<String, List<Transaction>> map = new HashMap<>();
        for(Category category : categories){
            List<Transaction> values = transactions.stream().filter(x -> x.getCategoryId().equals(category.getId())).toList();
            map.put(category.getName(), values);
        }
        return new BudgetDetailDTO(budget, usedAmount, map);
    }

    @Transactional
    public void deleteById(Long id){
        if(!budgetRepository.existsById(id)){
            throw new BusinessException(BudgetMessage.BUDGET_NOT_EXISTED, HttpStatus.NOT_FOUND);
        }

        budgetRepository.deleteById(id);
        budgetCategoryRepository.deleteByBudgetId(id);
    }

}
