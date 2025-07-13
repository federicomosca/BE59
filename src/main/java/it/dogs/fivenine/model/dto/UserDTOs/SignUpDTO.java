package it.dogs.fivenine.model.dto.UserDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

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
   
   public SignUpDTO() {}
   
   public SignUpDTO(String email, String username, String password) {
       this.email = email;
       this.username = username;
       this.password = password;
   }
   
   public String getEmail() { return email; }
   public void setEmail(String email) { this.email = email; }
   
   public String getUsername() { return username; }
   public void setUsername(String username) { this.username = username; }
   
   public String getPassword() { return password; }
   public void setPassword(String password) { this.password = password; }
}