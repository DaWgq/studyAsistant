package com.school.controller;

import com.school.dto.Result;
import com.school.entity.Notification;
import com.school.entity.Student;
import com.school.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 首页控制器
 */
@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private HomeService homeService;

    /**
     * 获取首页统计数据
     */
    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        String userId = getUserIdFromToken(authorization);
        Map<String, Object> stats = homeService.getHomeStats(userId);
        return Result.success(stats);
    }

    /**
     * 获取快捷操作菜单
     */
    @GetMapping("/quick-actions")
    public Result<List<Map<String, Object>>> getQuickActions() {
        return Result.success(homeService.getQuickActions());
    }

    /**
     * 获取通知列表
     */
    @GetMapping("/notifications")
    public Result<Map<String, Object>> getNotifications(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int page_size) {
        String userId = getUserIdFromToken(authorization);
        
        Page<Notification> notificationPage = homeService.getNotifications(userId, page, page_size);
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", notificationPage.getContent().stream()
                .map(this::notificationToMap)
                .collect(Collectors.toList()));
        result.put("total", notificationPage.getTotalElements());
        result.put("page", page);
        result.put("page_size", page_size);

        return Result.success(result);
    }

    /**
     * 标记通知为已读
     */
    @PutMapping("/notifications/{id}/read")
    public Result<Void> markNotificationAsRead(@PathVariable Integer id) {
        homeService.markNotificationAsRead(id);
        return Result.success();
    }

    /**
     * 获取未读通知数量
     */
    @GetMapping("/notifications/unread-count")
    public Result<Map<String, Long>> getUnreadNotificationCount(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        String userId = getUserIdFromToken(authorization);
        long count = homeService.getUnreadNotificationCount(userId);
        Map<String, Long> result = new HashMap<>();
        result.put("count", count);
        return Result.success(result);
    }

    private String getUserIdFromToken(String authorization) {
        // 简化处理，实际应从 token 中解析
        if (authorization != null && authorization.startsWith("Bearer ")) {
            // 这里应该使用 JwtUtil 解析 token 获取 userId
            // 暂时返回默认用户 ID
            return "teacher001";
        }
        return "teacher001";
    }

    private Map<String, Object> notificationToMap(Notification notification) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", notification.getId());
        map.put("title", notification.getTitle());
        map.put("time", notification.getCreatedAt().toLocalTime().toString().substring(0, 5));
        map.put("type", notification.getType());
        map.put("is_read", notification.getIsRead() == 1);
        return map;
    }
}
