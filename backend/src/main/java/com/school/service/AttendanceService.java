package com.school.service;

import java.time.LocalDate;
import java.util.Map;

/**
 * 考勤服务接口
 */
public interface AttendanceService {

    /**
     * 获取考勤统计
     */
    Map<String, Object> getAttendanceStats(LocalDate date);

    /**
     * 提交考勤
     */
    void submitAttendance(String studentId, LocalDate date, String status, String remark);

    /**
     * 获取学生考勤记录
     */
    Map<String, Object> getStudentAttendance(String studentId, int page, int size);
}
