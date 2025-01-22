package com.example.lms.service;

import com.example.lms.dto.UserDTO;
import com.example.lms.entity.User;
import com.example.lms.exception.RoleNotFoundException;

import java.util.List;

public interface UserService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    public List<UserDTO> getAllUsers() throws RoleNotFoundException;
    public UserDTO getUserById(Long id);
    public UserDTO updateUser(Long id, UserDTO userDto);
    public void deleteUser(Long id);
    void save(User user);
}
