package com.example.lms.service;

import com.example.lms.dto.ApiResponseDto;
import com.example.lms.dto.SignInRequestDto;
import com.example.lms.dto.SignUpRequestDto;
import com.example.lms.entity.Role;
import com.example.lms.entity.User;
import com.example.lms.exception.RoleNotFoundException;
import com.example.lms.exception.UserAlreadyExistsException;
import com.example.lms.factories.RoleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleFactory roleFactory;

    @Override
    public ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, RoleNotFoundException {
        if(userService.existsByEmail(signUpRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided email already exists. Try sign in or provide another email.");
        }
        if (userService.existsByUsername(signUpRequestDto.getUserName())){
            throw new UserAlreadyExistsException("Registration Failed: Provided username already exists. Try sign in or provide another username.");
        }

        User user = createUser(signUpRequestDto);
        userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("User account has been successfully created.")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto) {
        return null;
    }

    private User createUser(SignUpRequestDto signUpRequestDto) throws RoleNotFoundException {
        return User.builder()
                .email(signUpRequestDto.getEmail())
                .username(signUpRequestDto.getUserName())
                .password(signUpRequestDto.getPassword())
                .enabled(true)
                .roles(determineRoles(signUpRequestDto.getRoles()))
                .build();
    }

    private Set<Role> determineRoles(Set<String> strRoles) throws RoleNotFoundException {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(roleFactory.getInstance("student"));
        }
        else {
            for (String role : strRoles) {
                roles.add(roleFactory.getInstance(role));
            }
        }

        return roles;
    }


}
