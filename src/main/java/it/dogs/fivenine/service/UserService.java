package it.dogs.fivenine.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.model.result.LoginResult;


@Service
public interface UserService {

    Long signUp(SignUpDTO dto);
    LoginResult login(LoginDTO dto);
    List<User> getUsers();
    Optional<User> findById(Long id);
    void updateEmail(Long userId, String newEmail);
    String changePassword(LoginDTO dto, String newPassword);
    String deactivate(LoginDTO dto);
    int deleteUser(LoginDTO dto);
    Set<Collection> getCollections(LoginDTO dto);
    Optional<User> findByEmail(String newEmail);
    User save(User user);
}