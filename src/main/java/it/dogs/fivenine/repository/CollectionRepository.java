package it.dogs.fivenine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
}