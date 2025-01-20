package com.example.lms.service;

import com.example.lms.dto.UserDTO;
import com.example.lms.entity.User;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean existsByName(String name) {
        return userRepository.existsByUsername(name);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean createUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) { //user != null
            return false;
        }

        // Manually map the fields from userDTO to newUser
        User newUser = new User();
        newUser.setUsername(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setRole(userDTO.getRole());

        // Save the new user to the database
        userRepository.save(newUser);
        return true;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole()
                )).toList();
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
                user.getRole()
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
                    updatedUser.getRole()
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
}
