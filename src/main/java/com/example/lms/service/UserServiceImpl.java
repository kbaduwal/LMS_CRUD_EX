package com.example.lms.service;

import com.example.lms.dto.UserDTO;
import com.example.lms.entity.Role;
import com.example.lms.entity.User;
import com.example.lms.enums.UserRole;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.exception.RoleNotFoundException;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<UserDTO> getAllUsers() throws RoleNotFoundException{
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(Role::getName) // Assuming Role::getName maps to UserRole or similar
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("No role found for the user"))
                        )).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("User", "id", id));

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(Role::getName) // Assuming Role::getName maps to UserRole or similar
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No role found for the user"))
        );
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDto) {

        Optional<User> userDetails = userRepository.findById(id);

        if(userDetails.isPresent()){
            User existingUser = userDetails.get();
            BeanUtils.copyProperties(userDto, existingUser);

            User updatedUser = userRepository.save(existingUser);

            return new UserDTO(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getPassword(),
                    updatedUser.getRoles().stream()
                            .map(Role::getName) // Assuming Role::getName maps to UserRole or similar
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("No role found for the user"))
            );

        }else {
            throw new ResourceNotFoundException("User","id",id);
        }

    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userId = userRepository.findById(id);

        if(userId.isPresent()){
            userRepository.delete(userId.get());
        }else {
            throw new ResourceNotFoundException("User","id",id);
        }

    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
