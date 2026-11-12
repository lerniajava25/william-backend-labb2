package org.example.backendlabb2.dtos;

import org.example.backendlabb2.entities.Comment;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        String commentText,
        UserDTO author,
        Long ticketId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
    public static CommentDTO from(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getCommentText(),
                UserDTO.from(comment.getAuthor()),
                comment.getTicket() == null ? null : comment.getTicket().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }
}
