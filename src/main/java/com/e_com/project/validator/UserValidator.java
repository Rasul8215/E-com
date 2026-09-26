package com.e_com.project.validator;


import com.e_com.project.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserValidator {

    @NotBlank(message = "Username must need")
    private String username;

    @NotBlank(message = "Name must need")
    private String name;


    @NotBlank(message = "Email must need")
    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Password must need")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
