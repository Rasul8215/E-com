package com.e_com.project.validator;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenValidator {

    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
