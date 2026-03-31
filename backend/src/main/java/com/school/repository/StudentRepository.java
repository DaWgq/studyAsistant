package com.school.repository;

import com.school.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 学生 Repository
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByStudentNo(String studentNo);

    boolean existsByStudentNo(String studentNo);

    @Query("SELECT s FROM Student s WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "s.name LIKE %:keyword% OR " +
           "s.studentNo LIKE %:keyword% OR " +
           "s.grade LIKE %:keyword%) AND " +
           "(:grade IS NULL OR :grade = '' OR s.grade = :grade) AND " +
           "(:status IS NULL OR :status = '' OR s.status = :status)")
    Page<Student> searchStudents(@Param("keyword") String keyword,
                                  @Param("grade") String grade,
                                  @Param("status") String status,
                                  Pageable pageable);

    long countByStatus(String status);

    long count();
}
