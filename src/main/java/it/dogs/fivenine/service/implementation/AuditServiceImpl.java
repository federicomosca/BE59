package it.dogs.fivenine.service.implementation;

import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.UserAuditLog;
import it.dogs.fivenine.repository.UserAuditLogRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    private final UserAuditLogRepository auditRepository;
    private final UserRepository userRepository;

    public AuditServiceImpl(UserAuditLogRepository auditRepository, UserRepository userRepository) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void logUserAction(Long userId, String action, String ipAddress, String userAgent, String details, boolean success) {
        UserAuditLog log = new UserAuditLog(userId, action, ipAddress, userAgent, details, success);
        auditRepository.save(log);
    }

    @Override
    public void logLoginAttempt(String username, String ipAddress, String userAgent, boolean success, String details) {
        Long userId = null;
        if (success) {
            var user = userRepository.findByUsername(username);
            userId = user != null ? user.getId() : null;
        }
        String action = success ? "LOGIN_SUCCESS" : "LOGIN_FAILED";
        logUserAction(userId, action, ipAddress, userAgent, "Username: " + username + ". " + details, success);
    }

    @Override
    public void logPasswordChange(Long userId, String ipAddress, String userAgent, boolean success) {
        String action = success ? "PASSWORD_CHANGE_SUCCESS" : "PASSWORD_CHANGE_FAILED";
        logUserAction(userId, action, ipAddress, userAgent, "Password change attempt", success);
    }

    @Override
    public void logEmailChange(Long userId, String ipAddress, String userAgent, boolean success) {
        String action = success ? "EMAIL_CHANGE_SUCCESS" : "EMAIL_CHANGE_FAILED";
        logUserAction(userId, action, ipAddress, userAgent, "Email change attempt", success);
    }

    @Override
    public void logAccountAction(Long userId, String action, String ipAddress, String userAgent, boolean success) {
        logUserAction(userId, action, ipAddress, userAgent, "Account action: " + action, success);
    }
}