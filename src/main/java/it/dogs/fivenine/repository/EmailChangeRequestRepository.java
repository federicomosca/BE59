package it.dogs.fivenine.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.EmailChangeRequest;

@Repository
public interface EmailChangeRequestRepository extends JpaRepository<EmailChangeRequest, Long>{
    
    @Query("SELECT e FROM EmailChangeRequest e WHERE e.token = :token AND e.expiresAt > :now")
    Optional<EmailChangeRequest> findValidToken(@Param("token") String token, @Param("now") LocalDateTime now);
}
