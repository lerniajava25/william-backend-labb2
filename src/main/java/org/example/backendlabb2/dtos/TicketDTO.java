package org.example.backendlabb2.dtos;

import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.entities.TicketStatus;

import java.time.LocalDateTime;

public record TicketDTO(
        Long id,
        String title,
        String description,
        TicketStatus status,
        UserDTO author,
        UserDTO assignee,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
    public static TicketDTO from(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                UserDTO.from(ticket.getAuthor()),
                UserDTO.from(ticket.getAssignee()),
                ticket.getCreatedAt(),
                ticket.getModifiedAt());
    }
}
