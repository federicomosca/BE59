package it.dogs.fivenine.service;

import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.SignUpDTO;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    Long signUp(SignUpDTO dto);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    void updateUser(Long id, User user);

    void deleteUser(Long id);
}