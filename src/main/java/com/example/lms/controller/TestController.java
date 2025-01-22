package com.example.lms.controller;

import com.example.lms.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {

    //Only user with "STUDENT" role can access this end point
    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponseDto<?>> StudentDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("Student Dashboard")
                        .build()
                );
    }

    //Only user with "TEACHER" role can access this end point
    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<ApiResponseDto<?>> TeacherDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message("Teacher Dashboard")
                                .build()
                );
    }

    //Only with user "LIBRARIAN" role can access this end point
    @GetMapping("/librarian")
    @PreAuthorize("hasRole('librarian')")
    public ResponseEntity<ApiResponseDto<?>> LibrarianDashboard() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("Librarian Dashboard")
                        .build()
                );
    }

    //User with "TEACHER" or "LIBRARIAN" roles can access this end pont
    @GetMapping("/teacherOrLibrarian")
    @PreAuthorize("hasRole('TEACHER') or hasRole('LIBRARIAN')")
    public ResponseEntity<ApiResponseDto<?>> TeacherOrLibrarianContent() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("Teacher or Librarian content.")
                        .build()
                );
    }

}
