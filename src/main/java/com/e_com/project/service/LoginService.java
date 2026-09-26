package com.e_com.project.service;

import com.e_com.project.models.User;
import com.e_com.project.repository.UserRepo;
import com.e_com.project.validator.LoginValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    private final UserRepo userRepo;
    private final JwtService jwtService;

    public LoginService(UserRepo userRepo, JwtService jwtService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    public Map<String, String> login(LoginValidator body) {
        return getToken(body.getUsername());
    }

    public Map<String, String> getTokenByRefreshToken(String token) {
        String username = jwtService.extractnameToken(token);
        return getToken(username);
    }


    private Map<String, String> getToken(String username) {
        try {
            User user = userRepo.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
            return Map.of(
                    "token", jwtService.generateToken(user.getEmail()),
                    "refreshToken", jwtService.generateRefreshToken(user.getEmail())
            );
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
