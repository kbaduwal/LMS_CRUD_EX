package com.example.lms.controller;

import com.example.lms.dto.UserDTO;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO createUserDTO) {
        boolean isCreated = userService.createUser(createUserDTO);

        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
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
