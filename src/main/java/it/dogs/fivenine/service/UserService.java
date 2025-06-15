package it.dogs.fivenine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;


@Service
public interface UserService {

    Long signUp(SignUpDTO dto);
    String login(LoginDTO dto);
    List<User> getUsers(String adminPassword);
    Optional<User> findById(Long id);
    String updateEmail(LoginDTO dto, String newEmail);
    String changePassword(LoginDTO dto, String newPassword);
    String unsubscribe(LoginDTO dto);
    List<Collection> getCollections(LoginDTO dto);
}