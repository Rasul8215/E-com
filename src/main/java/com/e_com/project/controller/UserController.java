package com.e_com.project.controller;

import com.e_com.project.models.User;
import com.e_com.project.service.UserService;
import com.e_com.project.validator.UserValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<User> postUser(@Valid @RequestBody UserValidator body) {
        User user = userService.createUser(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }
}
