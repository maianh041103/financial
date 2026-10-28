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
    boolean existsByNameAndUserId(String name, Long userId);
    boolean existsByNameAndUserIdAndIdNot(String name, Long userId, Long id);
    Optional<Category> findByIdAndUserId(Long id, Long userId);
    List<Category> findByIdInAndUserId(List<Long> ids, Long userId);

    @Query("SELECT c FROM Category c " +
            "WHERE (:#{#filter.keyword} IS NULL OR c.name LIKE %:#{#filter.keyword}%) " +
            "AND (:#{#filter.categoryType} IS NULL OR c.type = :#{#filter.categoryType}) " +
            "AND c.deletedAt IS NULL")
    Page<Category> filter(CategoryFilter filter, Pageable pageable);
}
