package com.school.repository;

import com.school.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通知 Repository
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Page<Notification> findByUserId(String userId, Pageable pageable);

    List<Notification> findByUserIdAndIsRead(String userId, Integer isRead);

    long countByUserIdAndIsRead(String userId, Integer isRead);

    void deleteByUserId(String userId);
}
