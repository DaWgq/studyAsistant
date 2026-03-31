package com.school.service;

import com.school.entity.Notification;
import com.school.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 首页服务接口
 */
public interface HomeService {

    /**
     * 获取首页统计数据
     */
    Map<String, Object> getHomeStats(String userId);

    /**
     * 获取快捷操作菜单
     */
    List<Map<String, Object>> getQuickActions();

    /**
     * 获取通知列表
     */
    Page<Notification> getNotifications(String userId, int page, int size);

    /**
     * 标记通知为已读
     */
    void markNotificationAsRead(Integer notificationId);

    /**
     * 获取未读通知数量
     */
    long getUnreadNotificationCount(String userId);

    /**
     * 获取首页学生列表（最新 10 个）
     */
    List<Student> getLatestStudents();
}
