package com.school.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(length = 32)
    private String id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 50)
    private String realName;

    @Column(unique = true, length = 20)
    private String employeeId;

    @Column(length = 20)
    private String role = "teacher";

    @Column(length = 255)
    private String avatarUrl;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column
    private Integer status = 1;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        }
    }
}
