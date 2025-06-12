package it.dogs.fivenine.service;

import it.dogs.fivenine.builder.UserBuilder;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.SignUpDTO;
import it.dogs.fivenine.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserBuilder builder;

    @Override
    public Long signUp(SignUpDTO dto) {
        return Stream.of(dto)
            .map(builder::build)
            .map(repository::save)
            .map(User::getId)
            .findFirst()
            .get();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void updateUser(Long id, User user) {
    }

    @Override
    public void deleteUser(Long id) {

    }
}
