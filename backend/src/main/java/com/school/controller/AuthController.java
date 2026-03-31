package com.school.controller;

import com.school.dto.Result;
import com.school.entity.User;
import com.school.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return Result.error(400, "用户名和密码不能为空");
        }

        String token = authService.login(username, password);
        User user = authService.getCurrentUser(token);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", userToMap(user));

        return Result.success(data);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String authorization) {
        String token = getTokenFromHeader(authorization);
        authService.logout(token);
        return Result.success();
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    public Result<Map<String, Object>> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        String token = getTokenFromHeader(authorization);
        User user = authService.getCurrentUser(token);
        return Result.success(userToMap(user));
    }

    private String getTokenFromHeader(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return authorization;
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("real_name", user.getRealName());
        map.put("employee_id", user.getEmployeeId());
        map.put("role", user.getRole());
        map.put("avatar_url", user.getAvatarUrl());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());
        return map;
    }
}
