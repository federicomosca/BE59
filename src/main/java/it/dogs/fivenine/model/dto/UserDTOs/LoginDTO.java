package it.dogs.fivenine.model.dto.UserDTOs;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
