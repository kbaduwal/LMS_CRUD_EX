package com.example.lms.controller;

import com.example.lms.dto.ApiResponseDto;
import com.example.lms.dto.SignInRequestDto;
import com.example.lms.dto.SignUpRequestDto;
import com.example.lms.dto.UserDTO;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.exception.RoleNotFoundException;
import com.example.lms.exception.UserAlreadyExistsException;
import com.example.lms.service.AuthService;
import com.example.lms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws RoleNotFoundException, UserAlreadyExistsException {

        return authService.signUpUser(signUpRequestDto);

    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseDto<?>> signInUser(@RequestBody @Valid
                                                        SignInRequestDto signInRequestDto){
        return authService.signInUser(signInRequestDto);
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws RoleNotFoundException {
        List<UserDTO> usersList = userService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity
                    .ok(user);

        }catch (RuntimeException e){
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long id,
                                                      @RequestBody UserDTO userBinding)
    {
        try {
            UserDTO updatedDetail = userService.updateUser(id, userBinding);
            return ResponseEntity.ok(updatedDetail);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try{
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }
}
