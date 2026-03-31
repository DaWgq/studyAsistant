package com.school.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 教师陪伴天数实体类
 */
@Data
@Entity
@Table(name = "teacher_days")
public class TeacherDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 32)
    private String userId;

    @Column
    private Integer totalDays = 0;

    @Column
    private LocalDate startDate;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
