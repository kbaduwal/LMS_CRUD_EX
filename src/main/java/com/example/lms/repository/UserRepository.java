package com.example.lms.repository;

import com.example.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
}
