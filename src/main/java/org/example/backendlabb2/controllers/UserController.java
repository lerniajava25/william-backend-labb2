package org.example.backendlabb2.controllers;

import org.example.backendlabb2.dtos.UserDTO;
import org.example.backendlabb2.dtos.UserRequestDTO;
import org.example.backendlabb2.entities.User;
import org.example.backendlabb2.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.isActive(),
                        user.getCreatedAt(),
                        user.getModifiedAt()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.isActive(),
                        user.getCreatedAt(),
                        user.getModifiedAt())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserDTO save(@RequestBody UserRequestDTO req) {
        User user = new User();
        user.setUsername(req.username());
        user.setPasswordHash(req.passwordHash());
        user.setRole(req.role());
        user.setActive(req.active());
        User saved = userService.save(user);

        return new UserDTO(
                saved.getId(),
                saved.getUsername(),
                saved.getRole(),
                saved.isActive(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO req) {
        User user = userService.findById(id).orElse(null);
        if(user == null) return ResponseEntity.notFound().build();

        user.setUsername(req.username());
        user.setPasswordHash(req.passwordHash());
        user.setRole(req.role());
        user.setActive(req.active());
        User updated = userService.save(user);

        return ResponseEntity.ok(new UserDTO(
                updated.getId(),
                updated.getUsername(),
                updated.getRole(),
                updated.isActive(),
                updated.getCreatedAt(),
                updated.getModifiedAt()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
