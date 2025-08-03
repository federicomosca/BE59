package it.dogs.fivenine.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.PasswordChangeRequest;

@Repository
public interface PasswordChangeRequestRepository extends JpaRepository<PasswordChangeRequest, Long>{
    
    @Query("SELECT p FROM PasswordChangeRequest p WHERE p.token = :token AND p.expiresAt > :now")
    Optional<PasswordChangeRequest> findValidToken(@Param("token") String token, @Param("now") LocalDateTime now);
}