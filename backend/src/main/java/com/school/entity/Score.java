package com.school.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩实体类
 */
@Data
@Entity
@Table(name = "scores", indexes = {
    @Index(name = "idx_student_id", columnList = "student_id"),
    @Index(name = "idx_term", columnList = "term")
})
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 32)
    private String studentId;

    @Column(nullable = false, length = 50)
    private String subject;

    @Column(length = 50)
    private String examType;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @Column
    private Integer classRank;

    @Column(length = 50)
    private String term;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
