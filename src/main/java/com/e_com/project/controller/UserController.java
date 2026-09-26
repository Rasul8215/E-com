package com.e_com.project.controller;

import com.e_com.project.models.User;
import com.e_com.project.service.UserService;
import com.e_com.project.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    ResponseEntity<?> postUser(@Valid @RequestBody UserValidator body) {
        try {
            User user = userService.createUser(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "An account with this email already exists"));
        }
    }

    @GetMapping
    ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }
}
