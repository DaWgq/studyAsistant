package com.school.controller;

import com.school.dto.Result;
import com.school.entity.Student;
import com.school.service.AttendanceService;
import com.school.service.ScoreService;
import com.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学生管理控制器
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ScoreService scoreService;

    /**
     * 获取学生列表
     */
    @GetMapping
    public Result<Map<String, Object>> getStudents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int page_size,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword) {
        
        Page<Student> studentPage = studentService.getStudents(page, page_size, grade, status, keyword);
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", studentPage.getContent().stream()
                .map(this::studentToMap)
                .collect(Collectors.toList()));
        result.put("total", studentPage.getTotalElements());
        result.put("page", page);
        result.put("page_size", page_size);

        return Result.success(result);
    }

    /**
     * 获取学生详情
     */
    @GetMapping("/{id}")
    public Result<Map<String, Object>> getStudent(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        Map<String, Object> data = studentToMap(student);
        
        // 添加统计数据
        Map<String, Object> stats = scoreService.getStudentScoreStats(id);
        Map<String, Object> attendanceStats = attendanceService.getStudentAttendance(id, 1, 30);
        
        Map<String, Object> combinedStats = new HashMap<>();
        combinedStats.put("avg_score", stats.get("avg_score"));
        combinedStats.put("class_rank", stats.get("class_rank"));
        combinedStats.put("attendance_rate", 100); // 默认 100%
        
        data.put("stats", combinedStats);
        
        return Result.success(data);
    }

    /**
     * 创建学生
     */
    @PostMapping
    public Result<Map<String, Object>> createStudent(@RequestBody Map<String, Object> request) {
        Student student = new Student();
        student.setStudentNo((String) request.get("student_no"));
        student.setName((String) request.get("name"));
        student.setGender((String) request.get("gender"));
        student.setGrade((String) request.get("grade"));
        student.setMajor((String) request.get("major"));
        student.setPhone((String) request.get("phone"));
        student.setAddress((String) request.get("address"));
        student.setEnrollmentYear((Integer) request.get("enrollment_year"));
        student.setStatus("在校");
        student.setAvatarUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=" + student.getName());

        Student created = studentService.createStudent(student);
        return Result.success(studentToMap(created));
    }

    /**
     * 更新学生信息
     */
    @PutMapping("/{id}")
    public Result<Map<String, Object>> updateStudent(
            @PathVariable String id, 
            @RequestBody Map<String, Object> request) {
        
        Student student = new Student();
        if (request.get("name") != null) student.setName((String) request.get("name"));
        if (request.get("gender") != null) student.setGender((String) request.get("gender"));
        if (request.get("grade") != null) student.setGrade((String) request.get("grade"));
        if (request.get("major") != null) student.setMajor((String) request.get("major"));
        if (request.get("phone") != null) student.setPhone((String) request.get("phone"));
        if (request.get("address") != null) student.setAddress((String) request.get("address"));
        if (request.get("status") != null) student.setStatus((String) request.get("status"));
        if (request.get("enrollment_year") != null) student.setEnrollmentYear((Integer) request.get("enrollment_year"));

        Student updated = studentService.updateStudent(id, student);
        return Result.success(studentToMap(updated));
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return Result.success();
    }

    /**
     * 根据学号获取学生
     */
    @GetMapping("/by-student-no/{studentNo}")
    public Result<Map<String, Object>> getStudentByStudentNo(@PathVariable String studentNo) {
        Student student = studentService.getStudentByStudentNo(studentNo);
        return Result.success(studentToMap(student));
    }

    /**
     * 获取学生成绩列表
     */
    @GetMapping("/{id}/scores")
    public Result<List<Map<String, Object>>> getStudentScores(@PathVariable String id) {
        List<Map<String, Object>> scores = scoreService.getScoresByStudentId(id).stream()
                .map(this::scoreToMap)
                .collect(Collectors.toList());
        return Result.success(scores);
    }

    private Map<String, Object> studentToMap(Student student) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", student.getId());
        map.put("student_no", student.getStudentNo());
        map.put("name", student.getName());
        map.put("gender", student.getGender());
        map.put("grade", student.getGrade());
        map.put("major", student.getMajor());
        map.put("avatar_url", student.getAvatarUrl());
        map.put("phone", student.getPhone());
        map.put("address", student.getAddress());
        map.put("status", student.getStatus());
        map.put("enrollment_year", student.getEnrollmentYear());
        return map;
    }

    private Map<String, Object> scoreToMap(com.school.entity.Score score) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", score.getId());
        map.put("subject", score.getSubject());
        map.put("exam_type", score.getExamType());
        map.put("score", score.getScore());
        map.put("class_rank", score.getClassRank());
        map.put("term", score.getTerm());
        return map;
    }
}
