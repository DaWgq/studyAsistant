package com.school.service.impl;

import com.school.entity.Score;
import com.school.entity.Student;
import com.school.exception.BusinessException;
import com.school.repository.ScoreRepository;
import com.school.repository.StudentRepository;
import com.school.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成绩服务实现
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Score> getScoresByStudentId(String studentId) {
        return scoreRepository.findByStudentIdOrderByScoreDesc(studentId);
    }

    @Override
    public List<Score> getScoresByStudentIdAndTerm(String studentId, String term) {
        return scoreRepository.findByStudentIdAndTerm(studentId, term);
    }

    @Override
    @Transactional
    public Score createScore(Score score) {
        // 验证学生是否存在
        Student student = studentRepository.findById(score.getStudentId())
                .orElseThrow(() -> new BusinessException(2001, "学生不存在"));

        return scoreRepository.save(score);
    }

    @Override
    @Transactional
    public Score updateScore(Integer id, Score score) {
        Score existingScore = scoreRepository.findById(id)
                .orElseThrow(() -> new BusinessException("成绩不存在"));

        if (score.getScore() != null) {
            existingScore.setScore(score.getScore());
        }
        if (score.getClassRank() != null) {
            existingScore.setClassRank(score.getClassRank());
        }
        if (score.getExamType() != null) {
            existingScore.setExamType(score.getExamType());
        }
        if (score.getSubject() != null) {
            existingScore.setSubject(score.getSubject());
        }
        if (score.getTerm() != null) {
            existingScore.setTerm(score.getTerm());
        }

        return scoreRepository.save(existingScore);
    }

    @Override
    @Transactional
    public void deleteScore(Integer id) {
        if (!scoreRepository.existsById(id)) {
            throw new BusinessException("成绩不存在");
        }
        scoreRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getStudentScoreStats(String studentId) {
        List<Score> scores = getScoresByStudentId(studentId);

        Map<String, Object> stats = new HashMap<>();

        if (scores.isEmpty()) {
            stats.put("avg_score", 0);
            stats.put("class_rank", 0);
            stats.put("total_subjects", 0);
            return stats;
        }

        // 计算平均分
        BigDecimal totalScore = scores.stream()
                .map(Score::getScore)
                .filter(s -> s != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double avgScore = totalScore.doubleValue() / scores.size();
        stats.put("avg_score", Math.round(avgScore));

        // 获取最好排名
        int bestRank = scores.stream()
                .map(Score::getClassRank)
                .filter(r -> r != null)
                .min(Integer::compareTo)
                .orElse(0);
        stats.put("class_rank", bestRank);

        stats.put("total_subjects", scores.size());

        return stats;
    }
}
