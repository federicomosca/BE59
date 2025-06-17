package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.builder.UserBuilder;
import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public String login(LoginDTO dto) {
        User u = repository.findByUsername(dto.getUsername());

        if(u.getPassword().equals(dto.getPassword())) 
        return "ok";
        return "ko";
    }

    @Override
    public List<User> getUsers(String adminPassword) {
        if(adminPassword.equals("lolWUT"))
        return repository.findAll();
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public String updateEmail(LoginDTO dto, String newEmail) {
        User u = repository.findByUsername(dto.getUsername());
            if(u.getPassword().equals(dto.getPassword())) {
                dto.setEmail(newEmail);
                return "ok"; 
            }
            return "ko";
    }

    @Override
    public String changePassword(LoginDTO dto, String newPassword) {
        User u = repository.findByUsername(dto.getUsername());
        if(u.getPassword().equals(dto.getPassword())){
            dto.setPassword(newPassword);
            return "ok";
        }
        return "ko";
    }

    @Override
    public String deactivate(LoginDTO dto) {
        User u = repository.findByUsername(dto.getUsername());
        if(u.getPassword().equals(dto.getPassword())){
            u.setActive(false);
            return "You successfully deactivated";
        }
        return "Wrong password.";
    }

    @Override
    public int deleteUser(LoginDTO dto) {
        User u = repository.authenticate(dto.getUsername(), dto.getPassword());
        if(u != null) { 
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
