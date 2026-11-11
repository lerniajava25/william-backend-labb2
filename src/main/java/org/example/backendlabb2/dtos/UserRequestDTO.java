package org.example.backendlabb2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.backendlabb2.entities.Role;

import java.time.LocalDateTime;

public record UserRequestDTO(
        @NotBlank String username,
        @NotBlank String passwordHash,
        @NotNull Role role,
        @NotNull boolean active) {
}
