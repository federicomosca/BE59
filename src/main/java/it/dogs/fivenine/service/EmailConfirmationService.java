package it.dogs.fivenine.service;

import it.dogs.fivenine.model.result.EmailConfirmationResult;

public interface EmailConfirmationService {
    void sendConfirmationEmail(Long userId, String email);
    EmailConfirmationResult confirmEmail(String token);
}