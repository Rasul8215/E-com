package com.e_com.project.service;


import com.e_com.project.models.User;
import com.e_com.project.repository.UserRepo;
import com.e_com.project.validator.UserValidator;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public User createUser(UserValidator input) {
        User user = userMapper.toEntity(input);
        return userRepo.save(user);
    }



}
