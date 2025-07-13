package it.dogs.fivenine.service.implementation;

import org.springframework.stereotype.Service;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestEmailChange'");
    }

    @Override
    public EmailConfirmationResult confirmEmailChange(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmEmailChange'");
    }

}
