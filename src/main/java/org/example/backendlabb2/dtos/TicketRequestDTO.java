package org.example.backendlabb2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.backendlabb2.entities.TicketStatus;
import org.example.backendlabb2.entities.User;

public record TicketRequestDTO(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull TicketStatus status,
        @NotNull User author,
        User assignee) {
}
