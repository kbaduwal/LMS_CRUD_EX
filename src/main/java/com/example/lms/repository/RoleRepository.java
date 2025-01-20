package com.example.lms.repository;

import com.example.lms.entity.Role;
import com.example.lms.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRole name);
}
