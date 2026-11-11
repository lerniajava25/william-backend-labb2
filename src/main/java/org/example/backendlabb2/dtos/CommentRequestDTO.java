package org.example.backendlabb2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.entities.User;

public record CommentRequestDTO(
        @NotBlank String commentText,
        @NotNull User author,
        @NotNull Ticket ticket) {
}
