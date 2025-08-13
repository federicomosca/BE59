package it.dogs.fivenine.repository;

import it.dogs.fivenine.model.domain.CollectionShare;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionShareRepository extends JpaRepository<CollectionShare, Long> {
    
    List<CollectionShare> findBySharedWithOrderBySharedAtDesc(User sharedWith);
    
    List<CollectionShare> findBySharedByOrderBySharedAtDesc(User sharedBy);
    
    List<CollectionShare> findByCollectionOrderBySharedAtDesc(Collection collection);
    
    @Query("SELECT COUNT(cs) FROM CollectionShare cs WHERE cs.sharedWith = :user AND cs.viewedAt IS NULL")
    long countUnviewedShares(@Param("user") User user);
    
    @Query("SELECT cs FROM CollectionShare cs WHERE cs.sharedWith = :user AND cs.viewedAt IS NULL ORDER BY cs.sharedAt DESC")
    List<CollectionShare> findUnviewedShares(@Param("user") User user);
    
    Optional<CollectionShare> findByCollectionAndSharedByAndSharedWith(Collection collection, User sharedBy, User sharedWith);
}