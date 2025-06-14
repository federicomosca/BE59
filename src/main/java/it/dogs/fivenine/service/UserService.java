package it.dogs.fivenine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;


@Service
public interface UserService {
    Long signUp(SignUpDTO dto);

    String login(LoginDTO dto);

    List<User> getUsers(String adminPassword);
}