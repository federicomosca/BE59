package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.EmailUpdateDTO;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.PasswordChangeDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.model.result.AccountActionResult;
import it.dogs.fivenine.model.result.EmailChangeResult;
import it.dogs.fivenine.model.result.EmailConfirmationResult;
import it.dogs.fivenine.model.result.LoginResult;
import it.dogs.fivenine.model.result.PasswordChangeResult;
import it.dogs.fivenine.model.result.PasswordConfirmationResult;
import it.dogs.fivenine.model.result.SignUpResult;
import it.dogs.fivenine.service.EmailChangeService;
import it.dogs.fivenine.service.PasswordChangeService;
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
    
    @Autowired
    private PasswordChangeService passwordChangeService;

    // <------------------------------ general user endpoints ------------------------------>

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResult> signUp(@RequestBody SignUpDTO dto) {
        SignUpResult result = userService.signUp(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResult> login(@RequestBody LoginDTO dto) {
        LoginResult result = userService.login(dto);
        return ResponseEntity.ok(result);
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
    
    @PostMapping("/{userId}/password/request")
    public ResponseEntity<PasswordChangeResult> requestPasswordChange(@PathVariable Long userId, @RequestBody PasswordChangeDTO dto) {
        PasswordChangeResult result = passwordChangeService.requestPasswordChange(userId, dto.getCurrentPassword(), dto.getNewPassword());
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/password/confirm")
    public ResponseEntity<PasswordConfirmationResult> confirmPasswordChange(@RequestParam String token) {
        PasswordConfirmationResult result = passwordChangeService.confirmPasswordChange(token);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/deactivate")
    public ResponseEntity<AccountActionResult> deactivateAccount(@RequestBody LoginDTO dto) {
        AccountActionResult result = userService.deactivate(dto);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/reactivate")
    public ResponseEntity<AccountActionResult> reactivateAccount(@RequestBody LoginDTO dto) {
        AccountActionResult result = userService.reactivate(dto);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<AccountActionResult> deleteAccount(@RequestBody LoginDTO dto) {
        AccountActionResult result = userService.deleteUser(dto);
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
