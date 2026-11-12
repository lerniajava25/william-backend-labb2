package org.example.backendlabb2.dtos;

import org.example.backendlabb2.entities.Role;
import org.example.backendlabb2.entities.User;

import java.time.LocalDateTime;

public record UserDTO(Long id, String username, Role role, boolean active, LocalDateTime createdAt, LocalDateTime modifiedAt) {
    public static UserDTO from(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }
}
