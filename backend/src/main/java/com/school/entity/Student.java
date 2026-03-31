package com.school.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@Entity
@Table(name = "students", indexes = {
    @Index(name = "idx_grade", columnList = "grade"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_student_no", columnList = "student_no")
})
public class Student {

    @Id
    @Column(length = 32)
    private String id;

    @Column(unique = true, nullable = false, length = 20)
    private String studentNo;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 10)
    private String gender;

    @Column(length = 255)
    private String avatarUrl;

    @Column(nullable = false, length = 50)
    private String grade;

    @Column(length = 50)
    private String major;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String status = "在校";

    @Column
    private Integer enrollmentYear;

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
