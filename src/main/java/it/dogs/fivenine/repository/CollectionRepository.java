package it.dogs.fivenine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.sets.CollectionType;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    
    List<Collection> findByUser(User user);
    
    List<Collection> findByUserAndType(User user, CollectionType type);
    
    Optional<Collection> findByUserAndTypeAndName(User user, CollectionType type, String name);
    
    @Query("SELECT c FROM Collection c WHERE c.user.id = :userId")
    List<Collection> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT c FROM Collection c WHERE c.user.id = :userId AND c.type = :type")
    List<Collection> findByUserIdAndType(@Param("userId") Long userId, @Param("type") CollectionType type);
}