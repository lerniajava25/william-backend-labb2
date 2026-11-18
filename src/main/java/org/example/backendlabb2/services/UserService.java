package org.example.backendlabb2.services;

import jakarta.transaction.Transactional;
import org.example.backendlabb2.entities.Role;
import org.example.backendlabb2.entities.User;
import org.example.backendlabb2.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void setPassword(User user, String rawPassword) {
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
    }

    public List<User> findAll() {
        return userRepository.findAllBy();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User updateRole(Long id, Role role, String callerUsername) {
        var user = findAccessibleUser(id, callerUsername);
        user.setRole(role);
        return save(user);
    }

    public User updateStatus(Long id, boolean active, String callerUsername) {
        var user = findAccessibleUser(id, callerUsername);
        user.setActive(active);
        return save(user);
    }

    private User findAccessibleUser(Long id, String callerUsername) {
        var caller = userRepository.findByUsername(callerUsername).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(caller.getRole() != Role.ADMIN || !caller.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var user = findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(caller.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
