package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.EmailUpdateDTO;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.model.result.EmailChangeResult;
import it.dogs.fivenine.model.result.EmailConfirmationResult;
import it.dogs.fivenine.service.EmailChangeService;
import it.dogs.fivenine.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailChangeService emailChangeService;

    // <------------------------------ general user endpoints ------------------------------>

    @PostMapping("/signUp")
    public Long signUp(@RequestBody SignUpDTO dto) {
        return userService.signUp(dto);
    }

    @GetMapping("/login")
    public String login(@RequestBody LoginDTO dto) {
        return userService.login(dto);
    }

    @PostMapping("/{userId}/email/request")
    public ResponseEntity<EmailChangeResult> requestEmailChange(@PathVariable Long userId, @RequestBody EmailUpdateDTO dto) {
        EmailChangeResult result = emailChangeService.requestEmailChange(userId, dto.getNewEmail());
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/email/confirm")
    public ResponseEntity<EmailConfirmationResult> confirmEmailChange(@RequestParam String token) {
        EmailConfirmationResult result = emailChangeService.confirmEmailChange(token);
        return ResponseEntity.ok(result);
    }

    // <------------------------------ privileged user endpoints ------------------------------>

    // should create an endpoint to let admins signup and automatically receive a
    // password for privileged actions

    @PostMapping("/get-all")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    public Optional<User> getUserById(@RequestBody Long id) {
        return userService.findById(id);
    }

}
