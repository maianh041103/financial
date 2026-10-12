package com.study.financial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.financial.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 255)
    @JsonIgnore
    private String password;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;
}