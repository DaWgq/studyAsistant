package com.school.service.impl;

import com.school.entity.User;
import com.school.exception.BusinessException;
import com.school.repository.UserRepository;
import com.school.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 个人中心服务实现
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String NOTIFICATION_SETTINGS_PREFIX = "notification_settings:";

    @Override
    public User getProfile(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(1005, "用户不存在"));
    }

    @Override
    public User updateProfile(String userId, User user) {
        User existingUser = getProfile(userId);

        if (user.getRealName() != null) {
            existingUser.setRealName(user.getRealName());
        }
        if (user.getPhone() != null) {
            existingUser.setPhone(user.getPhone());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getAvatarUrl() != null) {
            existingUser.setAvatarUrl(user.getAvatarUrl());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        User user = getProfile(userId);

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new BusinessException(1006, "原密码错误");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public Map<String, Object> getNotificationSettings(String userId) {
        Map<String, Object> settings = (Map<String, Object>) redisTemplate.opsForValue()
                .get(NOTIFICATION_SETTINGS_PREFIX + userId);

        if (settings == null) {
            settings = new HashMap<>();
            settings.put("notifications_enabled", true);
            settings.put("sound_enabled", true);
            settings.put("vibration_enabled", false);
        }

        return settings;
    }

    @Override
    public void updateNotificationSettings(String userId, Map<String, Object> settings) {
        redisTemplate.opsForValue().set(
                NOTIFICATION_SETTINGS_PREFIX + userId,
                settings,
                30,
                TimeUnit.DAYS
        );
    }
}
