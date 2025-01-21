package com.example.lms.dataSeeders;

import com.example.lms.entity.Role;
import com.example.lms.enums.UserRole;
import com.example.lms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleDataSeeder {

    @Autowired
    private RoleRepository roleRepository;

    public void LoadRoles(ContextRefreshedEvent event) {
        List<UserRole> roles = Arrays.stream(UserRole.values()).toList();
        for (UserRole eRole : roles) {
            if(roleRepository.findByName(eRole)==null){
                roleRepository.save(new Role(eRole));
            }
        }

    }

}
