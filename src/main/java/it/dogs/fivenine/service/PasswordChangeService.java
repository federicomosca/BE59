package it.dogs.fivenine.service;

import it.dogs.fivenine.model.result.PasswordChangeResult;
import it.dogs.fivenine.model.result.PasswordConfirmationResult;

public interface PasswordChangeService {
    
    PasswordChangeResult requestPasswordChange(Long userId, String currentPassword, String newPassword);
    PasswordConfirmationResult confirmPasswordChange(String token);
}