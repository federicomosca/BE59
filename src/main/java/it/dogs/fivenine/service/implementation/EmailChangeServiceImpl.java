package it.dogs.fivenine.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.EmailChangeRequest;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.result.EmailChangeError;
import it.dogs.fivenine.model.result.EmailChangeResult;
import it.dogs.fivenine.model.result.EmailConfirmationResult;
import it.dogs.fivenine.repository.EmailChangeRequestRepository;
import it.dogs.fivenine.service.EmailChangeService;
import it.dogs.fivenine.service.UserService;

@Service
public class EmailChangeServiceImpl implements EmailChangeService {

    private final EmailChangeRequestRepository repository;
    private final UserService userService;

    public EmailChangeServiceImpl(EmailChangeRequestRepository repository,
            UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public EmailChangeResult requestEmailChange(Long userId, String newEmail) {
        
        // check yser exists
        Optional<User> userOpt = userService.findById(userId);
        if(userOpt.isEmpty()) {
            return EmailChangeResult.failure(EmailChangeError.USER_NOT_FOUND, "User not found");
        }

        // validate email format
        if(!newEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return EmailChangeResult.failure(EmailChangeError.INVALID_FORMAT, "Invalid email format");
        }

        // check email not already taken
        Optional<User> existingUser = userService.findByEmail(newEmail);
        if(existingUser.isPresent()) {
            return EmailChangeResult.failure(EmailChangeError.ALREADY_EXISTS, "Email already in use");
        }

        // generate token
        String token = UUID.randomUUID().toString();

        // create expiration time
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        EmailChangeRequest request = new EmailChangeRequest();
        request.setUserId(userId);
        request.setNewEmail(newEmail);
        request.setToken(token);
        request.setExpiresAt(expiresAt);
        request.setCreatedAt(LocalDateTime.now());

        repository.save(request);

        return EmailChangeResult.success();
    }

    @Override
    public EmailConfirmationResult confirmEmailChange(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmEmailChange'");
    }

}
