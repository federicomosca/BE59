package it.dogs.fivenine.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.PasswordChangeRequest;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.result.PasswordChangeError;
import it.dogs.fivenine.model.result.PasswordChangeResult;
import it.dogs.fivenine.model.result.PasswordConfirmationError;
import it.dogs.fivenine.model.result.PasswordConfirmationResult;
import it.dogs.fivenine.repository.PasswordChangeRequestRepository;
import it.dogs.fivenine.service.PasswordChangeService;
import it.dogs.fivenine.service.UserService;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

    private final PasswordChangeRequestRepository repository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordChangeServiceImpl(PasswordChangeRequestRepository repository, 
                                   UserService userService) {
        this.repository = repository;
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public PasswordChangeResult requestPasswordChange(Long userId, String currentPassword, String newPassword) {
        
        // check user exists
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return PasswordChangeResult.failure(PasswordChangeError.USER_NOT_FOUND, "User not found");
        }
        
        User user = userOpt.get();
        
        // verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return PasswordChangeResult.failure(PasswordChangeError.INVALID_CURRENT_PASSWORD, "Current password is incorrect");
        }
        
        // validate new password strength (minimum 8 characters)
        if (newPassword.length() < 8) {
            return PasswordChangeResult.failure(PasswordChangeError.WEAK_PASSWORD, "New password must be at least 8 characters");
        }
        
        // generate token
        String token = UUID.randomUUID().toString();
        
        // hash new password
        String newPasswordHash = passwordEncoder.encode(newPassword);
        
        // create expiration time (24 hours)
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);
        
        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setUserId(userId);
        request.setNewPasswordHash(newPasswordHash);
        request.setToken(token);
        request.setExpiresAt(expiresAt);
        request.setCreatedAt(LocalDateTime.now());
        
        repository.save(request);
        
        return PasswordChangeResult.success();
    }

    @Override
    public PasswordConfirmationResult confirmPasswordChange(String token) {
        // find valid token
        Optional<PasswordChangeRequest> requestOpt = repository.findValidToken(token, LocalDateTime.now());
        
        if (requestOpt.isEmpty()) {
            return PasswordConfirmationResult.failure(
                PasswordConfirmationError.TOKEN_NOT_FOUND, 
                "Token not found or expired"
            );
        }
        
        PasswordChangeRequest request = requestOpt.get();
        
        // update user password
        Optional<User> userOpt = userService.findById(request.getUserId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(request.getNewPasswordHash());
            userService.save(user);
        }
        
        // delete the used token
        repository.delete(request);
        
        return PasswordConfirmationResult.success();
    }
}