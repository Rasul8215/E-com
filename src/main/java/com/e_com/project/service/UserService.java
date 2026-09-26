package com.e_com.project.service;


import com.e_com.project.models.User;
import com.e_com.project.repository.UserRepo;
import com.e_com.project.validator.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserValidator input) {
        String encryptedPassword = passwordEncoder.encode(input.getPassword());
        User user = userMapper.toEntity(input);
        user.setPassword(encryptedPassword);
        return userRepo.save(user);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }



}
