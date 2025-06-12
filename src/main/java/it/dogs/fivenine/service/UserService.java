package it.dogs.fivenine.service;

import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User createUser(User user);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}