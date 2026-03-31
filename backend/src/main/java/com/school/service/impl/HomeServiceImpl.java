package com.school.service.impl;

import com.school.entity.Notification;
import com.school.entity.Student;
import com.school.entity.TeacherDays;
import com.school.exception.BusinessException;
import com.school.repository.NotificationRepository;
import com.school.repository.StudentRepository;
import com.school.repository.TeacherDaysRepository;
import com.school.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * 首页服务实现
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TeacherDaysRepository teacherDaysRepository;

    @Override
    public Map<String, Object> getHomeStats(String userId) {
        Map<String, Object> stats = new HashMap<>();

        // 学生总数
        long totalStudents = studentRepository.count();
        stats.put("total_students", totalStudents);
        stats.put("total_students_change", 12); // 模拟新增学生数

        // 今日考勤统计
        LocalDate today = LocalDate.now();
        long presentCount = 0; // 实际应从考勤表统计
        long absentCount = studentRepository.countByStatus("缺勤");
        long attendanceRate = totalStudents > 0 ? 
            (long) ((totalStudents - absentCount) * 100.0 / totalStudents) : 100;

        stats.put("today_attendance_rate", attendanceRate);
        stats.put("today_absent_count", absentCount);

        // 教师陪伴天数
        int teacherDays = teacherDaysRepository.findByUserId(userId)
                .map(TeacherDays::getTotalDays)
                .orElse(342);
        stats.put("teacher_days", teacherDays);

        return stats;
    }

    @Override
    public List<Map<String, Object>> getQuickActions() {
        List<Map<String, Object>> actions = new ArrayList<>();

        Map<String, Object> action1 = new HashMap<>();
        action1.put("id", 1);
        action1.put("icon", "➕");
        action1.put("label", "录入学生");
        action1.put("route", "/pages/add/add");
        actions.add(action1);

        Map<String, Object> action2 = new HashMap<>();
        action2.put("id", 2);
        action2.put("icon", "📅");
        action2.put("label", "考勤打卡");
        action2.put("route", "/pages/attendance/attendance");
        actions.add(action2);

        Map<String, Object> action3 = new HashMap<>();
        action3.put("id", 3);
        action3.put("icon", "📖");
        action3.put("label", "成绩管理");
        action3.put("route", "/pages/score/score");
        actions.add(action3);

        Map<String, Object> action4 = new HashMap<>();
        action4.put("id", 4);
        action4.put("icon", "🎓");
        action4.put("label", "综合评价");
        action4.put("route", "/pages/evaluate/evaluate");
        actions.add(action4);

        return actions;
    }

    @Override
    public Page<Notification> getNotifications(String userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return notificationRepository.findByUserId(userId, pageRequest);
    }

    @Override
    @Transactional
    public void markNotificationAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(3001, "通知不存在"));
        notification.setIsRead(1);
        notificationRepository.save(notification);
    }

    @Override
    public long getUnreadNotificationCount(String userId) {
        return notificationRepository.countByUserIdAndIsRead(userId, 0);
    }

    @Override
    public List<Student> getLatestStudents() {
        return studentRepository.findAll(PageRequest.of(0, 10)).getContent();
    }
}
