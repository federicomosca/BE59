package it.dogs.fivenine.model.dto.UserDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
public class SignUpDTO {

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Size(max = 7)
    @Pattern(regexp = "^[a-z0-9._-]+$")
    @JsonProperty("username")
    private String username;

    @NotBlank
    @Size(min = 8)
    @JsonProperty("password")
    private String password;

    @Builder
    public SignUpDTO(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}