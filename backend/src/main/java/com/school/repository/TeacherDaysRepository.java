package com.school.repository;

import com.school.entity.TeacherDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 教师陪伴天数 Repository
 */
@Repository
public interface TeacherDaysRepository extends JpaRepository<TeacherDays, Integer> {

    Optional<TeacherDays> findByUserId(String userId);

    void deleteByUserId(String userId);
}
