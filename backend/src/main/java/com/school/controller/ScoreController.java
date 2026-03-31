package com.school.controller;

import com.school.dto.Result;
import com.school.entity.Score;
import com.school.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 成绩控制器
 */
@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    /**
     * 获取学生成绩列表
     */
    @GetMapping("/student/{studentId}")
    public Result<List<Map<String, Object>>> getScoresByStudentId(@PathVariable String studentId) {
        List<Map<String, Object>> scores = scoreService.getScoresByStudentId(studentId).stream()
                .map(this::scoreToMap)
                .collect(Collectors.toList());
        return Result.success(scores);
    }

    /**
     * 获取学生某学期成绩
     */
    @GetMapping("/student/{studentId}/term/{term}")
    public Result<List<Map<String, Object>>> getScoresByStudentIdAndTerm(
            @PathVariable String studentId,
            @PathVariable String term) {
        List<Map<String, Object>> scores = scoreService.getScoresByStudentIdAndTerm(studentId, term).stream()
                .map(this::scoreToMap)
                .collect(Collectors.toList());
        return Result.success(scores);
    }

    /**
     * 录入成绩
     */
    @PostMapping
    public Result<Map<String, Object>> createScore(@RequestBody Map<String, Object> request) {
        Score score = new Score();
        score.setStudentId((String) request.get("student_id"));
        score.setSubject((String) request.get("subject"));
        score.setExamType((String) request.get("exam_type"));
        
        Object scoreObj = request.get("score");
        if (scoreObj instanceof Number) {
            score.setScore(new BigDecimal(scoreObj.toString()));
        }
        
        Object rankObj = request.get("class_rank");
        if (rankObj instanceof Number) {
            score.setClassRank(((Number) rankObj).intValue());
        }
        
        score.setTerm((String) request.get("term"));

        Score created = scoreService.createScore(score);
        return Result.success(scoreToMap(created));
    }

    /**
     * 更新成绩
     */
    @PutMapping("/{id}")
    public Result<Map<String, Object>> updateScore(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> request) {
        
        Score score = new Score();
        
        Object scoreObj = request.get("score");
        if (scoreObj instanceof Number) {
            score.setScore(new BigDecimal(scoreObj.toString()));
        }
        
        Object rankObj = request.get("class_rank");
        if (rankObj instanceof Number) {
            score.setClassRank(((Number) rankObj).intValue());
        }
        
        if (request.get("exam_type") != null) {
            score.setExamType((String) request.get("exam_type"));
        }
        if (request.get("subject") != null) {
            score.setSubject((String) request.get("subject"));
        }
        if (request.get("term") != null) {
            score.setTerm((String) request.get("term"));
        }

        Score updated = scoreService.updateScore(id, score);
        return Result.success(scoreToMap(updated));
    }

    /**
     * 删除成绩
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteScore(@PathVariable Integer id) {
        scoreService.deleteScore(id);
        return Result.success();
    }

    private Map<String, Object> scoreToMap(Score score) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", score.getId());
        map.put("student_id", score.getStudentId());
        map.put("subject", score.getSubject());
        map.put("exam_type", score.getExamType());
        map.put("score", score.getScore());
        map.put("class_rank", score.getClassRank());
        map.put("term", score.getTerm());
        return map;
    }
}
