package com.study.financial.repository;

import com.study.financial.entity.Category;
import com.study.financial.request.filter.CategoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT COUNT(c) > 0 FROM Category c " +
            "WHERE c.name = :name " +
            "AND c.userId = :userId " +
            "AND c.deletedAt IS NULL")
    boolean existsByNameAndUserId(String name, Long userId);

    @Query("SELECT COUNT(c) > 0 FROM Category c " +
            "WHERE c.name = :name " +
            "AND c.userId = :userId " +
            "AND c.id <> :id " +
            "AND c.deletedAt IS NULL")
    boolean existsByNameAndUserIdAndIdNot(String name, Long userId, Long id);

    @Query("SELECT c FROM Category c " +
            "WHERE c.id = :id " +
            "AND c.userId = :userId " +
            "AND c.deletedAt IS NULL")
    Optional<Category> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT c FROM Category c " +
            "WHERE c.id IN :ids " +
            "AND c.userId = :userId " +
            "AND c.deletedAt IS NULL")
    List<Category> findByIdInAndUserId(List<Long> ids, Long userId);

    @Query("SELECT c FROM Category c " +
            "WHERE (:#{#filter.userId} IS NULL OR c.userId = :#{#filter.userId}) " +
            "AND (:#{#filter.keyword} IS NULL OR c.name LIKE %:#{#filter.keyword}%) " +
            "AND (:#{#filter.categoryType} IS NULL OR c.type = :#{#filter.categoryType}) " +
            "AND c.deletedAt IS NULL")
    Page<Category> filter(CategoryFilter filter, Pageable pageable);
}
