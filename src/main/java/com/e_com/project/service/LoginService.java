package com.e_com.project.service;

import com.e_com.project.models.User;
import com.e_com.project.repository.UserRepo;
import com.e_com.project.validator.LoginValidator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepo userRepo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, String> login(LoginValidator body) {
        User user = userRepo.findByEmail(body.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return createTokens(user);
    }

    public Map<String, String> getTokenByRefreshToken(String token) {
        String username = jwtService.extractnameToken(token);
        return getToken(username);
    }

    private Map<String, String> getToken(String username) {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));
        return createTokens(user);
    }

    private Map<String, String> createTokens(User user) {
        return Map.of(
                "token", jwtService.generateToken(user.getEmail()),
                "refreshToken", jwtService.generateRefreshToken(user.getEmail())
        );
    }

}
