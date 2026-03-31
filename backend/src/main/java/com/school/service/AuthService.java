package com.school.service;

import com.school.entity.User;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     */
    String login(String username, String password);

    /**
     * 用户登出
     */
    void logout(String token);

    /**
     * 获取当前用户信息
     */
    User getCurrentUser(String token);

    /**
     * 验证 Token
     */
    boolean validateToken(String token);
}
