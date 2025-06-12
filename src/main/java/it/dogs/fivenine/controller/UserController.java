package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.SignUpDTO;
import it.dogs.fivenine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/signUp")
    public Long signUp(@RequestBody SignUpDTO dto) {
        return userService.signUp(dto);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
