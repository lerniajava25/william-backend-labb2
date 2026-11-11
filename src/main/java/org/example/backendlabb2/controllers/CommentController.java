package org.example.backendlabb2.controllers;

import org.example.backendlabb2.dtos.CommentDTO;
import org.example.backendlabb2.dtos.CommentRequestDTO;
import org.example.backendlabb2.entities.Comment;
import org.example.backendlabb2.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getCommentText(),
                        comment.getAuthor(),
                        comment.getTicket(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> findById(@PathVariable Long id) {
        return commentService.findById(id)
                .map(comment -> ResponseEntity.ok(new CommentDTO(
                        comment.getId(),
                        comment.getCommentText(),
                        comment.getAuthor(),
                        comment.getTicket(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CommentDTO save(@RequestBody CommentRequestDTO req) {
        Comment comment = new Comment();
        comment.setCommentText(req.commentText());
        comment.setAuthor(req.author());
        comment.setTicket(req.ticket());
        Comment saved = commentService.save(comment);

        return new CommentDTO(
                saved.getId(),
                saved.getCommentText(),
                saved.getAuthor(),
                saved.getTicket(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Long id, @RequestBody CommentRequestDTO req) {
        Comment comment = commentService.findById(id).orElse(null);
        if(comment == null) return ResponseEntity.notFound().build();

        comment.setCommentText(req.commentText());
        comment.setAuthor(req.author());
        comment.setTicket(req.ticket());
        Comment updated = commentService.save(comment);

        return ResponseEntity.ok(new CommentDTO(
                updated.getId(),
                updated.getCommentText(),
                updated.getAuthor(),
                updated.getTicket(),
                updated.getCreatedAt(),
                updated.getModifiedAt()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
