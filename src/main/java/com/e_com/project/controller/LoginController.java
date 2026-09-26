package com.e_com.project.controller;

import com.e_com.project.models.User;
import com.e_com.project.service.LoginService;
import com.e_com.project.validator.LoginValidator;
import com.e_com.project.validator.RefreshTokenValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginValidator payload) {
        return ResponseEntity.status(HttpStatus.OK).body(loginService.login(payload));
    }

    @PostMapping("/token")
    ResponseEntity<Map<String, String>> renewToken(@Valid @RequestBody RefreshTokenValidator payload) {
        return  ResponseEntity.status(HttpStatus.OK).body(loginService.getTokenByRefreshToken(payload.getRefreshToken()));
    }



}
