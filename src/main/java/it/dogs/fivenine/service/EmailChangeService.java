package it.dogs.fivenine.service;

import it.dogs.fivenine.model.result.EmailChangeResult;
import it.dogs.fivenine.model.result.EmailConfirmationResult;

public interface EmailChangeService {
    
    EmailChangeResult requestEmailChange(Long userId, String newEmail);
    EmailConfirmationResult confirmEmailChange(String token);
}
