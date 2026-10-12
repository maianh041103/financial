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
            "WHERE c.name LIKE %:#{#filter.name} " +
            "AND c.deletedAt IS NULL")
    Page<Category> filter(CategoryFilter filter, Pageable pageable);
}
