package com.study.financial.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "budget_category")
@Builder
public class BudgetCategory extends BaseEntity{
    @Column(name = "budget_id")
    private Long budgetId;

    @Column(name = "category_id")
    private Long categoryId;
}
