package com.example.lms.service;

import com.example.lms.dto.UserDTO;

import java.util.List;

public interface UserService {
    public boolean createUser(UserDTO userDTO);
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Long id);
    public UserDTO updateUser(Long id, UserDTO userDto);
    public void deleteUser(Long id);
}
