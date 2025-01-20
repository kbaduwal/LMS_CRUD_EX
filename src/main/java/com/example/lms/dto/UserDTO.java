package com.example.lms.dto;

import com.example.lms.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;


    private String email;

    private String password;

    private UserRole role;


}
