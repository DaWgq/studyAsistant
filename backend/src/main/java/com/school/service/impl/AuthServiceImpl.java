package com.school.service.impl;

import com.school.entity.User;
import com.school.exception.BusinessException;
import com.school.repository.UserRepository;
import com.school.service.AuthService;
import com.school.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_PREFIX = "token:";
    private static final long TOKEN_EXPIRE_HOURS = 7 * 24;

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(1001, "用户名或密码错误"));

        if (user.getStatus() != 1) {
            throw new BusinessException(1004, "用户已被禁用");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BusinessException(1001, "用户名或密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        // 将 token 存入 Redis
        redisTemplate.opsForValue().set(
            TOKEN_PREFIX + token, 
            user.getId(), 
            TOKEN_EXPIRE_HOURS, 
            TimeUnit.HOURS
        );

        return token;
    }

    @Override
    public void logout(String token) {
        if (token != null) {
            redisTemplate.delete(TOKEN_PREFIX + token);
        }
    }

    @Override
    public User getCurrentUser(String token) {
        if (token == null || !jwtUtil.validateToken(token)) {
            throw new BusinessException(1002, "Token 无效");
        }

        String userId = jwtUtil.getUserIdFromToken(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(1005, "用户不存在"));
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token) && 
               redisTemplate.hasKey(TOKEN_PREFIX + token);
    }
}
