package it.dogs.fivenine.model.dto.UserDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailUpdateDTO {

    @Email
    @NotBlank
   private String newEmail;
   
   public EmailUpdateDTO() {}
   
   public EmailUpdateDTO(String newEmail) {
       this.newEmail = newEmail;
   }
   
   public String getNewEmail() {
       return newEmail;
   }
   
   public void setNewEmail(String newEmail) {
       this.newEmail = newEmail;
   }
}