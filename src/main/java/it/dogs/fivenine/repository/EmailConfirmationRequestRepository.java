package it.dogs.fivenine.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.EmailConfirmationRequest;

@Repository
public interface EmailConfirmationRequestRepository extends JpaRepository<EmailConfirmationRequest, Long> {
    Optional<EmailConfirmationRequest> findByToken(String token);
    Optional<EmailConfirmationRequest> findByUserId(Long userId);
}