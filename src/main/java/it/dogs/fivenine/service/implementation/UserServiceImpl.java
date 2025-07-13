package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.builder.UserBuilder;
import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserBuilder builder;

    public UserServiceImpl(UserRepository repository, UserBuilder builder) {
        this.repository = repository;
        this.builder = builder;
    }

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
    public String login(LoginDTO dto) {
        User u = repository.findByUsername(dto.getUsername());

        if (u.getPassword().equals(dto.getPassword()))
            return "ok";
        return "ko";
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void updateEmail(Long userId, String newEmail) {
        User u = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        u.setEmail(newEmail);
        repository.save(u);
    }

    @Override
    public String changePassword(LoginDTO dto, String newPassword) {
        User u = repository.findByUsername(dto.getUsername());
        if (u.getPassword().equals(dto.getPassword())) {
            dto.setPassword(newPassword);
            return "ok";
        }
        return "ko";
    }

    @Override
    public String deactivate(LoginDTO dto) {
        User u = repository.findByUsername(dto.getUsername());
        if (u.getPassword().equals(dto.getPassword())) {
            u.setActive(false);
            return "You successfully deactivated";
        }
        return "Wrong password.";
    }

    @Override
    public int deleteUser(LoginDTO dto) {
        User u = repository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (u != null) {
            repository.delete(u);
            return 0;
        }
        return 1;
    }

    @Override
    public Set<Collection> getCollections(LoginDTO dto) {
        User u = repository.findByUsername(dto.getUsername());
        Set<Collection> c = u.getCollections();
        return c;
    }
}
