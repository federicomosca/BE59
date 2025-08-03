package it.dogs.fivenine.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.EmailConfirmationRequest;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.result.EmailConfirmationError;
import it.dogs.fivenine.model.result.EmailConfirmationResult;
import it.dogs.fivenine.repository.EmailConfirmationRequestRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.AuditService;
import it.dogs.fivenine.service.EmailConfirmationService;

@Service
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private final EmailConfirmationRequestRepository confirmationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final AuditService auditService;

    public EmailConfirmationServiceImpl(EmailConfirmationRequestRepository confirmationRepository,
                                      UserRepository userRepository,
                                      JavaMailSender mailSender,
                                      AuditService auditService) {
        this.confirmationRepository = confirmationRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.auditService = auditService;
    }

    @Override
    public void sendConfirmationEmail(Long userId, String email) {
        try {
            // Remove any existing confirmation request for this user
            confirmationRepository.findByUserId(userId).ifPresent(confirmationRepository::delete);

            // Create new confirmation request
            EmailConfirmationRequest request = new EmailConfirmationRequest();
            request.setUserId(userId);
            request.setEmail(email);
            request.setToken(UUID.randomUUID().toString());
            request.setCreatedAt(LocalDateTime.now());
            request.setExpiresAt(LocalDateTime.now().plusHours(24)); // 24 hour expiration

            confirmationRepository.save(request);

            // Send email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Confirm your email address - Fivenine");
            message.setText("Please click the following link to confirm your email address:\n\n" +
                           "http://localhost:8080/users/email/confirm-registration?token=" + request.getToken() + "\n\n" +
                           "This link will expire in 24 hours.\n\n" +
                           "If you did not create an account, please ignore this email.");

            mailSender.send(message);
            auditService.logUserAction(userId, "EMAIL_CONFIRMATION_SENT", null, null, "Confirmation email sent to: " + email, true);
            
        } catch (Exception e) {
            auditService.logUserAction(userId, "EMAIL_CONFIRMATION_FAILED", null, null, "Failed to send confirmation email: " + e.getMessage(), false);
            throw new RuntimeException("Failed to send confirmation email", e);
        }
    }

    @Override
    public EmailConfirmationResult confirmEmail(String token) {
        try {
            Optional<EmailConfirmationRequest> requestOpt = confirmationRepository.findByToken(token);
            
            if (requestOpt.isEmpty()) {
                auditService.logUserAction(null, "EMAIL_CONFIRMATION_FAILED", null, null, "Invalid token", false);
                return EmailConfirmationResult.failure(EmailConfirmationError.INVALID_TOKEN, "Invalid confirmation token");
            }

            EmailConfirmationRequest request = requestOpt.get();

            if (request.getConfirmed()) {
                auditService.logUserAction(request.getUserId(), "EMAIL_CONFIRMATION_FAILED", null, null, "Token already used", false);
                return EmailConfirmationResult.failure(EmailConfirmationError.TOKEN_ALREADY_USED, "Email already confirmed");
            }

            if (LocalDateTime.now().isAfter(request.getExpiresAt())) {
                auditService.logUserAction(request.getUserId(), "EMAIL_CONFIRMATION_FAILED", null, null, "Token expired", false);
                return EmailConfirmationResult.failure(EmailConfirmationError.TOKEN_EXPIRED, "Confirmation token has expired");
            }

            // Mark request as confirmed
            request.setConfirmed(true);
            confirmationRepository.save(request);

            // Update user's email confirmed status
            Optional<User> userOpt = userRepository.findById(request.getUserId());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setEmailConfirmed(true);
                userRepository.save(user);
                auditService.logUserAction(user.getId(), "EMAIL_CONFIRMATION_SUCCESS", null, null, "Email confirmed successfully", true);
                return EmailConfirmationResult.success();
            } else {
                auditService.logUserAction(request.getUserId(), "EMAIL_CONFIRMATION_FAILED", null, null, "User not found", false);
                return EmailConfirmationResult.failure(EmailConfirmationError.USER_NOT_FOUND, "User not found");
            }

        } catch (Exception e) {
            auditService.logUserAction(null, "EMAIL_CONFIRMATION_FAILED", null, null, "Confirmation error: " + e.getMessage(), false);
            return EmailConfirmationResult.failure(EmailConfirmationError.CONFIRMATION_ERROR, "Email confirmation failed: " + e.getMessage());
        }
    }
}