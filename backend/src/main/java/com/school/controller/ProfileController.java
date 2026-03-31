package com.school.controller;

import com.school.dto.Result;
import com.school.entity.User;
import com.school.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心控制器
 */
@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * 获取个人信息
     */
    @GetMapping
    public Result<Map<String, Object>> getProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        String userId = getUserIdFromToken(authorization);
        User user = profileService.getProfile(userId);
        return Result.success(userToMap(user));
    }

    /**
     * 更新个人信息
     */
    @PutMapping
    public Result<Map<String, Object>> updateProfile(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Map<String, Object> request) {
        
        String userId = getUserIdFromToken(authorization);
        User user = new User();
        
        if (request.get("real_name") != null) {
            user.setRealName((String) request.get("real_name"));
        }
        if (request.get("phone") != null) {
            user.setPhone((String) request.get("phone"));
        }
        if (request.get("email") != null) {
            user.setEmail((String) request.get("email"));
        }
        if (request.get("avatar_url") != null) {
            user.setAvatarUrl((String) request.get("avatar_url"));
        }

        User updated = profileService.updateProfile(userId, user);
        return Result.success(userToMap(updated));
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<Void> changePassword(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Map<String, String> request) {
        
        String userId = getUserIdFromToken(authorization);
        String oldPassword = request.get("old_password");
        String newPassword = request.get("new_password");

        if (oldPassword == null || newPassword == null) {
            return Result.error(400, "密码不能为空");
        }

        profileService.changePassword(userId, oldPassword, newPassword);
        return Result.success();
    }

    /**
     * 获取通知设置
     */
    @GetMapping("/notification-settings")
    public Result<Map<String, Object>> getNotificationSettings(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        String userId = getUserIdFromToken(authorization);
        return Result.success(profileService.getNotificationSettings(userId));
    }

    /**
     * 更新通知设置
     */
    @PutMapping("/notification-settings")
    public Result<Void> updateNotificationSettings(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody Map<String, Object> settings) {
        
        String userId = getUserIdFromToken(authorization);
        profileService.updateNotificationSettings(userId, settings);
        return Result.success();
    }

    private String getUserIdFromToken(String authorization) {
        // 简化处理，实际应从 token 中解析
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return "teacher001";
        }
        return "teacher001";
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("real_name", user.getRealName());
        map.put("employee_id", user.getEmployeeId());
        map.put("role", user.getRole().equals("teacher") ? "班主任" : user.getRole());
        map.put("avatar_url", user.getAvatarUrl());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());
        return map;
    }
}
