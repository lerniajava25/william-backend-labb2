package org.example.backendlabb2.dtos;

import org.example.backendlabb2.entities.TicketStatus;
import org.example.backendlabb2.entities.User;

import java.time.LocalDateTime;

public record TicketDTO(
        Long id,
        String title,
        String description,
        TicketStatus status,
        User author,
        User assignee,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
}
