package it.dogs.fivenine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
