package com.school.service;

import com.school.entity.User;

import java.util.Map;

/**
 * 个人中心服务接口
 */
public interface ProfileService {

    /**
     * 获取用户个人信息
     */
    User getProfile(String userId);

    /**
     * 更新用户个人信息
     */
    User updateProfile(String userId, User user);

    /**
     * 修改密码
     */
    void changePassword(String userId, String oldPassword, String newPassword);

    /**
     * 获取通知设置
     */
    Map<String, Object> getNotificationSettings(String userId);

    /**
     * 更新通知设置
     */
    void updateNotificationSettings(String userId, Map<String, Object> settings);
}
