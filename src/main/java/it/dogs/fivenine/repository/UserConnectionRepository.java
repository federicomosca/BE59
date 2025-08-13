package it.dogs.fivenine.repository;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.UserConnection;
import it.dogs.fivenine.model.domain.enums.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {
    
    Optional<UserConnection> findByRequesterAndAddressee(User requester, User addressee);
    
    List<UserConnection> findByRequesterAndStatus(User requester, ConnectionStatus status);
    
    List<UserConnection> findByAddresseeAndStatus(User addressee, ConnectionStatus status);
    
    @Query("SELECT uc FROM UserConnection uc WHERE (uc.requester = :user OR uc.addressee = :user) AND uc.status = :status")
    List<UserConnection> findByUserAndStatus(@Param("user") User user, @Param("status") ConnectionStatus status);
    
    @Query("SELECT uc FROM UserConnection uc WHERE uc.addressee = :user AND uc.status = 'PENDING'")
    List<UserConnection> findPendingRequestsForUser(@Param("user") User user);
    
    @Query("SELECT COUNT(uc) FROM UserConnection uc WHERE uc.addressee = :user AND uc.status = 'PENDING'")
    long countPendingRequestsForUser(@Param("user") User user);
    
    @Query("SELECT uc FROM UserConnection uc WHERE ((uc.requester = :user1 AND uc.addressee = :user2) OR (uc.requester = :user2 AND uc.addressee = :user1)) AND uc.status = 'ACCEPTED'")
    Optional<UserConnection> findAcceptedConnectionBetween(@Param("user1") User user1, @Param("user2") User user2);
}