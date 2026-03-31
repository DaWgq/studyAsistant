package com.school.service;

import com.school.entity.Score;

import java.util.List;
import java.util.Map;

/**
 * 成绩服务接口
 */
public interface ScoreService {

    /**
     * 获取学生成绩列表
     */
    List<Score> getScoresByStudentId(String studentId);

    /**
     * 获取学生某学期成绩
     */
    List<Score> getScoresByStudentIdAndTerm(String studentId, String term);

    /**
     * 录入成绩
     */
    Score createScore(Score score);

    /**
     * 更新成绩
     */
    Score updateScore(Integer id, Score score);

    /**
     * 删除成绩
     */
    void deleteScore(Integer id);

    /**
     * 获取学生平均成绩和排名
     */
    Map<String, Object> getStudentScoreStats(String studentId);
}
