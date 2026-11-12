package org.example.backendlabb2.controllers;

import jakarta.validation.Valid;
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
                .map(UserDTO::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserDTO.from(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserDTO save(@Valid @RequestBody UserRequestDTO req) {
        User user = new User();
        user.setUsername(req.username());
        user.setPasswordHash(req.password());
        user.setRole(req.role());
        user.setActive(req.active());
        return UserDTO.from(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO req) {
        User user = userService.findById(id).orElse(null);
        if(user == null) return ResponseEntity.notFound().build();

        user.setUsername(req.username());
        user.setPasswordHash(req.password());
        user.setRole(req.role());
        user.setActive(req.active());
        return ResponseEntity.ok(UserDTO.from(userService.save(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
