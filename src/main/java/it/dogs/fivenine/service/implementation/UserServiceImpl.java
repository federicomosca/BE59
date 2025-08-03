package it.dogs.fivenine.service.implementation;

import org.modelmapper.ModelMapper;
import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.model.dto.UserDTOs.SignUpDTO;
import it.dogs.fivenine.model.result.LoginError;
import it.dogs.fivenine.model.result.LoginResult;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.UserService;
import it.dogs.fivenine.util.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository repository, ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Long signUp(SignUpDTO dto) {
        // Check for duplicate username
        if (repository.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        
        // Check for duplicate email
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = modelMapper.map(dto, User.class);
        
        // Hash password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // Set registration date
        user.setRegistrationDate(new Date());
        
        return repository.save(user).getId();
    }

    @Override
    public LoginResult login(LoginDTO dto) {
        User user = repository.findByUsername(dto.getUsername());
        
        if (user == null) {
            return LoginResult.failure(LoginError.USER_NOT_FOUND, "User not found");
        }
        
        if (!user.getActive()) {
            return LoginResult.failure(LoginError.ACCOUNT_INACTIVE, "Account is inactive");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return LoginResult.failure(LoginError.INVALID_CREDENTIALS, "Invalid credentials");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        return LoginResult.success(token, user.getId(), user.getUsername());
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
        if (u != null && passwordEncoder.matches(dto.getPassword(), u.getPassword())) {
            u.setActive(false);
            repository.save(u);
            return "You successfully deactivated";
        }
        return "Wrong password.";
    }

    @Override
    public int deleteUser(LoginDTO dto) {
        User u = repository.findByUsername(dto.getUsername());
        if (u != null && passwordEncoder.matches(dto.getPassword(), u.getPassword())) {
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

    @Override
    public Optional<User> findByEmail(String newEmail) {
        return repository.findByEmail(newEmail);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
