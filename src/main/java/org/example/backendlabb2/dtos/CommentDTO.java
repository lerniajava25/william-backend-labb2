package org.example.backendlabb2.dtos;

import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.entities.User;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        String commentText,
        User author,
        Ticket ticket,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
}
