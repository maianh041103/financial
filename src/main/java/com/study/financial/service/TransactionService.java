package com.study.financial.service;

import com.study.financial.dto.TransactionDTO;
import com.study.financial.entity.Category;
import com.study.financial.entity.Transaction;
import com.study.financial.exception.BusinessException;
import com.study.financial.messages.CategoryMessage;
import com.study.financial.messages.TransactionMessage;
import com.study.financial.repository.CategoryRepository;
import com.study.financial.repository.TransactionRepository;
import com.study.financial.request.TransactionRequest;
import com.study.financial.request.filter.TransactionFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public void validateCreate(TransactionRequest request, Long userId){
        if(request.getCategoryId() != null){
            categoryRepository.findByIdAndUserId(request.getCategoryId(), userId)
                    .orElseThrow(() -> new BusinessException(CategoryMessage.CATEGORY_NOT_EXISTED, HttpStatus.NOT_FOUND));
        }
        if(request.getTransactionDate().isAfter(LocalDate.now())){
            throw new BusinessException(TransactionMessage.TRANSACTION_DATE_MAX, HttpStatus.BAD_REQUEST);
        }
    }

    public Transaction create(TransactionRequest request){
        Long userId = JwtService.getUserId();
        validateCreate(request, userId);

        Transaction transaction = Transaction.builder()
                .transactionType(request.getTransactionType())
                .categoryId(request.getCategoryId())
                .amount(request.getAmount())
                .name(request.getName())
                .amount(request.getAmount())
                .userId(userId)
                .transactionDate(request.getTransactionDate())
                .paymentMethod(request.getPaymentMethod())
                .note(request.getNote())
                .build();
        return transactionRepository.save(transaction);
    }

    public Page<TransactionDTO> filter(TransactionFilter filter, int page, int limit){
        Pageable pageable = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        filter.setUserId(JwtService.getUserId());
        return transactionRepository.filter(filter, pageable);
    }

    public TransactionDTO findById(Long id){
        Long userId = JwtService.getUserId();
        Transaction transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException(TransactionMessage.TRANSACTION_NOT_EXISTED, HttpStatus.NOT_FOUND));
        Category category = categoryRepository.findByIdAndUserId(id, userId).orElse(null);
        return new TransactionDTO(transaction, category == null ? "" : category.getName());
    }

    public Transaction update(Long id, TransactionRequest request){
        Long userId = JwtService.getUserId();

        Transaction transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException(TransactionMessage.TRANSACTION_NOT_EXISTED, HttpStatus.NOT_FOUND));
        validateCreate(request, userId);
        transaction.setCategoryId(request.getCategoryId());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setName(request.getName());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setNote(request.getNote());
        return transactionRepository.save(transaction);
    }

    public void deleteById(Long id){
        Transaction transaction = transactionRepository.findByIdAndUserId(id, JwtService.getUserId())
                .orElseThrow(() -> new BusinessException(TransactionMessage.TRANSACTION_NOT_EXISTED, HttpStatus.NOT_FOUND));

        transaction.setDeletedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

}
