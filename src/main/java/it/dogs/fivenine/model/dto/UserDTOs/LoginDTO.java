package it.dogs.fivenine.model.dto.UserDTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String email;
}
