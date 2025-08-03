package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.UserAuditLog;

public interface AuditService {
    
    void logUserAction(Long userId, String action, String ipAddress, String userAgent, String details, boolean success);
    void logLoginAttempt(String username, String ipAddress, String userAgent, boolean success, String details);
    void logPasswordChange(Long userId, String ipAddress, String userAgent, boolean success);
    void logEmailChange(Long userId, String ipAddress, String userAgent, boolean success);
    void logAccountAction(Long userId, String action, String ipAddress, String userAgent, boolean success);
}