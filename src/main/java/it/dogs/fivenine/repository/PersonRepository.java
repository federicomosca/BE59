package it.dogs.fivenine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    
    Optional<Person> findByName(String name);
    List<Person> findByNameContainingIgnoreCase(String name);
}