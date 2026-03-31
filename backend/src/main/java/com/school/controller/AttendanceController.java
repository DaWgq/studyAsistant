package com.school.controller;

import com.school.dto.Result;
import com.school.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * 考勤控制器
 */
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 获取考勤统计
     */
    @GetMapping("/stats")
    public Result<Map<String, Object>> getAttendanceStats(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return Result.success(attendanceService.getAttendanceStats(date));
    }

    /**
     * 提交考勤
     */
    @PostMapping
    public Result<Void> submitAttendance(@RequestBody Map<String, Object> request) {
        String studentId = (String) request.get("student_id");
        String dateStr = (String) request.get("date");
        String status = (String) request.get("status");
        String remark = (String) request.get("remark");

        if (studentId == null || status == null) {
            return Result.error(400, "学生 ID 和状态不能为空");
        }

        LocalDate date = dateStr != null ? LocalDate.parse(dateStr) : LocalDate.now();
        attendanceService.submitAttendance(studentId, date, status, remark);
        return Result.success();
    }

    /**
     * 获取学生考勤记录
     */
    @GetMapping("/student/{studentId}")
    public Result<Map<String, Object>> getStudentAttendance(
            @PathVariable String studentId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int size) {
        return Result.success(attendanceService.getStudentAttendance(studentId, page, size));
    }
}
