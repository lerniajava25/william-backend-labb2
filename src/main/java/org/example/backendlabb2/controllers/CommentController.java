package org.example.backendlabb2.controllers;

import jakarta.validation.Valid;
import org.example.backendlabb2.dtos.CommentDTO;
import org.example.backendlabb2.dtos.CommentRequestDTO;
import org.example.backendlabb2.entities.Comment;
import org.example.backendlabb2.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    CommentController(CommentService commentService) {
        this.commentService = commentService;
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
    public CommentDTO save(@Valid @RequestBody CommentRequestDTO req, Principal principal) {
        return CommentDTO.from(commentService.addComment(req.ticketId(), principal.getName(), req.commentText()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Long id, @Valid @RequestBody CommentRequestDTO req) {
        Comment comment = commentService.findById(id).orElse(null);
        if(comment == null) return ResponseEntity.notFound().build();

        comment.setCommentText(req.commentText());
        return ResponseEntity.ok(CommentDTO.from(commentService.save(comment)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
