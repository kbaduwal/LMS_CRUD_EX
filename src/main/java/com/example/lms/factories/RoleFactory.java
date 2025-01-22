package com.example.lms.factories;

import com.example.lms.entity.Role;
import com.example.lms.enums.UserRole;
import com.example.lms.exception.RoleNotFoundException;
import com.example.lms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {

    @Autowired
    RoleRepository roleRepository;

    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role){
            case "student" -> {
                return roleRepository.findByName(UserRole.STUDENT);
            }
            case "librarian" -> {
                return roleRepository.findByName(UserRole.LIBRARIAN);
            }
            case "teacher" -> {
                return roleRepository.findByName(UserRole.TEACHER);
            }
            default -> throw new RoleNotFoundException("No role found for role: " + role);
        }

    }
}
