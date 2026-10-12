package com.study.financial.service;

import com.study.financial.entity.Category;
import com.study.financial.exception.BusinessException;
import com.study.financial.messages.CategoryMessage;
import com.study.financial.repository.CategoryRepository;
import com.study.financial.request.CategoryRequest;
import com.study.financial.request.filter.CategoryFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public Category create(CategoryRequest request){
        Long userId = JwtService.getUserId();
        request.setName(request.getName().toUpperCase().trim());
        if(categoryRepository.existsByNameAndUserId(request.getName(), userId)){
            throw new BusinessException(CategoryMessage.CATEGORY_EXISTED, HttpStatus.BAD_REQUEST);
        }
        Category category = Category.builder()
                .name(request.getName())
                .color(request.getColor())
                .avatar(request.getAvatar())
                .description(request.getDescription())
                .userId(userId)
                .type(request.getType())
                .build();
        return categoryRepository.save(category);
    }

    public Category update(Long id, CategoryRequest request){
        Long userId = JwtService.getUserId();
        request.setName(request.getName().toUpperCase().trim());
        Category category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException(CategoryMessage.CATEGORY_NOT_EXISTED, HttpStatus.BAD_REQUEST));
        if(categoryRepository.existsByNameAndUserIdAndIdNot(request.getName(), userId, id)){
            throw new BusinessException(CategoryMessage.CATEGORY_EXISTED, HttpStatus.BAD_REQUEST);
        }
        category.setName(request.getName());
        category.setColor(request.getColor());
        category.setAvatar(request.getAvatar());
        category.setDescription(request.getDescription());
        category.setType(request.getType());
        return categoryRepository.save(category);
    }

    public Page<Category> filter(CategoryFilter filter, int page, int limit){
        Pageable pageable = PageRequest.of(
                page,
                limit,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return categoryRepository.filter(filter, pageable);
    }

    public Category findById(Long id){
        Long userId = JwtService.getUserId();
        return categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException(CategoryMessage.CATEGORY_NOT_EXISTED, HttpStatus.NOT_FOUND));
    }

    public void deleteById(Long id){
        Long userId = JwtService.getUserId();
        Category category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException(CategoryMessage.CATEGORY_NOT_EXISTED, HttpStatus.NOT_FOUND));
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }
}
