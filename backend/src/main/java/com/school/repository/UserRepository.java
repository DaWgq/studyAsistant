package com.school.repository;

import com.school.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmployeeId(String employeeId);

    boolean existsByUsername(String username);

    boolean existsByEmployeeId(String employeeId);
}
