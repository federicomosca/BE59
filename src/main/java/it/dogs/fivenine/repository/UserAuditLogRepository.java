package it.dogs.fivenine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.UserAuditLog;

@Repository
public interface UserAuditLogRepository extends JpaRepository<UserAuditLog, Long>{
    
    List<UserAuditLog> findByUserIdOrderByTimestampDesc(Long userId);
    List<UserAuditLog> findByActionOrderByTimestampDesc(String action);
}