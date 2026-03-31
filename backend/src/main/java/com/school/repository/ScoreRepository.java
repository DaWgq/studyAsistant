package com.school.repository;

import com.school.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 成绩 Repository
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    List<Score> findByStudentId(String studentId);

    List<Score> findByStudentIdAndTerm(String studentId, String term);

    List<Score> findByStudentIdOrderByScoreDesc(String studentId);
}
