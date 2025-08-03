package it.dogs.fivenine.service.implementation;

import org.modelmapper.ModelMapper;
import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.UserRole;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.model.result.AccountActionError;
import it.dogs.fivenine.model.result.AccountActionResult;
import it.dogs.fivenine.model.result.LoginError;
import it.dogs.fivenine.model.result.LoginResult;
import it.dogs.fivenine.model.result.SignUpResult;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.AuditService;
import it.dogs.fivenine.service.EmailConfirmationService;
import it.dogs.fivenine.service.UserService;
import it.dogs.fivenine.util.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuditService auditService;
    private final EmailConfirmationService emailConfirmationService;

    public UserServiceImpl(UserRepository repository, ModelMapper modelMapper, JwtUtil jwtUtil, 
                          AuditService auditService, EmailConfirmationService emailConfirmationService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.auditService = auditService;
        this.emailConfirmationService = emailConfirmationService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public SignUpResult signUp(SignUpDTO dto) {
        try {
            // Check for duplicate username
            if (repository.findByUsername(dto.getUsername()) != null) {
                auditService.logUserAction(null, "SIGNUP_FAILED", null, null, "Username already exists: " + dto.getUsername(), false);
                return SignUpResult.failure("USERNAME_EXISTS", "Username already exists");
            }
            
            // Check for duplicate email
            if (repository.findByEmail(dto.getEmail()).isPresent()) {
                auditService.logUserAction(null, "SIGNUP_FAILED", null, null, "Email already exists: " + dto.getEmail(), false);
                return SignUpResult.failure("EMAIL_EXISTS", "Email already exists");
            }
            
            User user = modelMapper.map(dto, User.class);
            
            // Hash password
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            
            // Set registration date
            user.setRegistrationDate(new Date());
            
            User savedUser = repository.save(user);
            auditService.logUserAction(savedUser.getId(), "SIGNUP_SUCCESS", null, null, "User registered successfully", true);
            
            // Send email confirmation
            try {
                emailConfirmationService.sendConfirmationEmail(savedUser.getId(), savedUser.getEmail());
            } catch (Exception e) {
                auditService.logUserAction(savedUser.getId(), "EMAIL_CONFIRMATION_SEND_FAILED", null, null, "Failed to send confirmation email: " + e.getMessage(), false);
            }
            
            return SignUpResult.success(savedUser.getId());
        } catch (Exception e) {
            auditService.logUserAction(null, "SIGNUP_FAILED", null, null, "Registration error: " + e.getMessage(), false);
            return SignUpResult.failure("REGISTRATION_ERROR", "Registration failed: " + e.getMessage());
        }
    }

    @Override
    public LoginResult login(LoginDTO dto) {
        User user = repository.findByUsername(dto.getUsername());
        
        if (user == null) {
            auditService.logLoginAttempt(dto.getUsername(), null, null, false, "User not found");
            return LoginResult.failure(LoginError.USER_NOT_FOUND, "User not found");
        }
        
        if (!user.getActive()) {
            auditService.logLoginAttempt(dto.getUsername(), null, null, false, "Account inactive");
            return LoginResult.failure(LoginError.ACCOUNT_INACTIVE, "Account is inactive");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            auditService.logLoginAttempt(dto.getUsername(), null, null, false, "Invalid password");
            return LoginResult.failure(LoginError.INVALID_CREDENTIALS, "Invalid credentials");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        auditService.logLoginAttempt(dto.getUsername(), null, null, true, "Login successful");
        
        return LoginResult.success(token, user.getId(), user.getUsername());
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void updateEmail(Long userId, String newEmail) {
        User u = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        u.setEmail(newEmail);
        repository.save(u);
    }

    @Override
    public String changePassword(LoginDTO dto, String newPassword) {
        User u = repository.findByUsername(dto.getUsername());
        if (u.getPassword().equals(dto.getPassword())) {
            dto.setPassword(newPassword);
            return "ok";
        }
        return "ko";
    }

    @Override
    public AccountActionResult deactivate(LoginDTO dto) {
        User user = repository.findByUsername(dto.getUsername());
        
        if (user == null) {
            auditService.logAccountAction(null, "DEACTIVATE_FAILED", null, null, false);
            return AccountActionResult.failure("DEACTIVATE", AccountActionError.USER_NOT_FOUND, "User not found");
        }
        
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            auditService.logAccountAction(user.getId(), "DEACTIVATE_FAILED", null, null, false);
            return AccountActionResult.failure("DEACTIVATE", AccountActionError.INVALID_PASSWORD, "Invalid password");
        }
        
        if (!user.getActive()) {
            auditService.logAccountAction(user.getId(), "DEACTIVATE_FAILED", null, null, false);
            return AccountActionResult.failure("DEACTIVATE", AccountActionError.ACCOUNT_ALREADY_INACTIVE, "Account is already inactive");
        }
        
        user.setActive(false);
        repository.save(user);
        auditService.logAccountAction(user.getId(), "DEACTIVATE_SUCCESS", null, null, true);
        
        return AccountActionResult.success("DEACTIVATE", "Account deactivated successfully");
    }

    @Override
    public AccountActionResult reactivate(LoginDTO dto) {
        User user = repository.findByUsername(dto.getUsername());
        
        if (user == null) {
            auditService.logAccountAction(null, "REACTIVATE_FAILED", null, null, false);
            return AccountActionResult.failure("REACTIVATE", AccountActionError.USER_NOT_FOUND, "User not found");
        }
        
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            auditService.logAccountAction(user.getId(), "REACTIVATE_FAILED", null, null, false);
            return AccountActionResult.failure("REACTIVATE", AccountActionError.INVALID_PASSWORD, "Invalid password");
        }
        
        if (user.getActive()) {
            auditService.logAccountAction(user.getId(), "REACTIVATE_FAILED", null, null, false);
            return AccountActionResult.failure("REACTIVATE", AccountActionError.ACCOUNT_ALREADY_ACTIVE, "Account is already active");
        }
        
        user.setActive(true);
        repository.save(user);
        auditService.logAccountAction(user.getId(), "REACTIVATE_SUCCESS", null, null, true);
        
        return AccountActionResult.success("REACTIVATE", "Account reactivated successfully");
    }

    @Override
    public AccountActionResult deleteUser(LoginDTO dto) {
        User user = repository.findByUsername(dto.getUsername());
        
        if (user == null) {
            auditService.logAccountAction(null, "DELETE_FAILED", null, null, false);
            return AccountActionResult.failure("DELETE", AccountActionError.USER_NOT_FOUND, "User not found");
        }
        
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            auditService.logAccountAction(user.getId(), "DELETE_FAILED", null, null, false);
            return AccountActionResult.failure("DELETE", AccountActionError.INVALID_PASSWORD, "Invalid password");
        }
        
        Long userId = user.getId();
        repository.delete(user);
        auditService.logAccountAction(userId, "DELETE_SUCCESS", null, null, true);
        
        return AccountActionResult.success("DELETE", "Account deleted successfully");
    }

    @Override
    public Set<Collection> getCollections(LoginDTO dto) {
        User u = repository.findByUsername(dto.getUsername());
        Set<Collection> c = u.getCollections();
        return c;
    }

    @Override
    public Optional<User> findByEmail(String newEmail) {
        return repository.findByEmail(newEmail);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public AccountActionResult makeAdmin(Long targetUserId, Long adminUserId) {
        // Check if requester is admin
        User adminUser = repository.findById(adminUserId).orElse(null);
        if (adminUser == null || !adminUser.isAdmin()) {
            auditService.logAccountAction(adminUserId, "MAKE_ADMIN_FAILED", null, null, false);
            return AccountActionResult.failure("MAKE_ADMIN", AccountActionError.OPERATION_FAILED, "Insufficient privileges");
        }

        User targetUser = repository.findById(targetUserId).orElse(null);
        if (targetUser == null) {
            auditService.logAccountAction(adminUserId, "MAKE_ADMIN_FAILED", null, null, false);
            return AccountActionResult.failure("MAKE_ADMIN", AccountActionError.USER_NOT_FOUND, "Target user not found");
        }

        if (targetUser.isAdmin()) {
            return AccountActionResult.failure("MAKE_ADMIN", AccountActionError.OPERATION_FAILED, "User is already admin");
        }

        targetUser.setRole(UserRole.ADMIN);
        repository.save(targetUser);
        auditService.logAccountAction(adminUserId, "MAKE_ADMIN_SUCCESS", null, null, true);

        return AccountActionResult.success("MAKE_ADMIN", "User promoted to admin successfully");
    }

    @Override
    public boolean isUserAdmin(Long userId) {
        User user = repository.findById(userId).orElse(null);
        return user != null && user.isAdmin();
    }
}
