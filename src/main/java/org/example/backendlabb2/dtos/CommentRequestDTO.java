package org.example.backendlabb2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequestDTO(
        @NotBlank String commentText,
        @NotNull Long authorId,
        @NotNull Long ticketId) {
}
