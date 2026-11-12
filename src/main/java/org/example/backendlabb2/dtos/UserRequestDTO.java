package org.example.backendlabb2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.backendlabb2.entities.Role;

public record UserRequestDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotNull Role role,
        @NotNull boolean active) {
}
