package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // <------------------------------ general user endpoints ------------------------------> 

    @GetMapping("login")
    public String login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }

    @PostMapping("/signUp")
    public Long signUp(@RequestBody SignUpDTO dto) {
        return userService.signUp(dto);
    }

    // <------------------------------ privileged user endpoints ------------------------------>

    // should create an endpoint to let admins signup and automatically receive a password for privileged actions

    public List<User> getUsers(@RequestBody String adminPassword) {
        return userService.getUsers(adminPassword);
    }
}
