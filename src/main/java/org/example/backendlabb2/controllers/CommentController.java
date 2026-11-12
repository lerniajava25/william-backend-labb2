package org.example.backendlabb2.controllers;

import jakarta.validation.Valid;
import org.example.backendlabb2.dtos.CommentDTO;
import org.example.backendlabb2.dtos.CommentRequestDTO;
import org.example.backendlabb2.entities.Comment;
import org.example.backendlabb2.services.CommentService;
import org.example.backendlabb2.services.TicketService;
import org.example.backendlabb2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final TicketService ticketService;

    CommentController(CommentService commentService, UserService userService, TicketService ticketService) {
        this.commentService = commentService;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<CommentDTO> findAll() {
        return commentService.findAll().stream()
                .map(CommentDTO::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> findById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(comment -> ResponseEntity.ok(CommentDTO.from(comment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CommentDTO save(@Valid  @RequestBody CommentRequestDTO req) {
        Comment comment = new Comment();
        applyRequest(comment, req);
        return CommentDTO.from(commentService.save(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Long id, @Valid @RequestBody CommentRequestDTO req) {
        Comment comment = commentService.findById(id).orElse(null);
        if(comment == null) return ResponseEntity.notFound().build();

        applyRequest(comment, req);
        return ResponseEntity.ok(CommentDTO.from(commentService.save(comment)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void applyRequest(Comment comment, CommentRequestDTO req) {
        comment.setCommentText(req.commentText());
        comment.setAuthor(userService.findById(req.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User " + req.authorId() + " does not exist")));
        comment.setTicket(ticketService.findById(req.ticketId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket " + req.ticketId() + " does not exist")));
    }
}
