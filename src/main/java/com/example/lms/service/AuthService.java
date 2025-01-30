package com.example.lms.service;

import com.example.lms.dto.ApiResponseDto;
import com.example.lms.dto.SignInRequestDto;
import com.example.lms.dto.SignUpRequestDto;
import com.example.lms.exception.RoleNotFoundException;
import com.example.lms.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;


public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;

    ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto);

    ResponseEntity<ApiResponseDto<?>> refreshAccessToken(String accessToken);
}
