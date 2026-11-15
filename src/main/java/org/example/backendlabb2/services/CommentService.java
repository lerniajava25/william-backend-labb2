package org.example.backendlabb2.services;

import jakarta.transaction.Transactional;
import org.example.backendlabb2.entities.Comment;
import org.example.backendlabb2.entities.TicketStatus;
import org.example.backendlabb2.repositories.CommentRepository;
import org.example.backendlabb2.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TicketService ticketService;

    CommentService(CommentRepository commentRepository, UserRepository userRepository, TicketService ticketService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.ticketService = ticketService;
    }

    public List<Comment> findAll() {
        return commentRepository.findAllBy();
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findByTicket(Long ticketId) {
        return commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment addComment(Long ticketId, String commentAuthorName, String commentText) {
        if(commentText == null || commentText.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var commentAuthor = userRepository.findByUsername(commentAuthorName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var ticket = ticketService.findAccessibleTicket(ticketId, commentAuthor);

        if(ticket.getStatus() == TicketStatus.CLOSED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var comment = new Comment();
        comment.setTicket(ticket);
        comment.setAuthor(commentAuthor);
        comment.setCommentText(commentText);
        return save(comment);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
