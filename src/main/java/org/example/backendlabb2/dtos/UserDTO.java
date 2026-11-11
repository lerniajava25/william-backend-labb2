package org.example.backendlabb2.dtos;

import org.example.backendlabb2.entities.Role;

import java.time.LocalDateTime;

public record UserDTO(Long id, String username, Role role, boolean active, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}
