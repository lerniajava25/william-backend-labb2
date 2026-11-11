package org.example.backendlabb2.repositories;

import org.example.backendlabb2.entities.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CommentRepository extends ListCrudRepository<Comment, Long> {
    @EntityGraph(value = "Comment.detail")
    List<Comment> findAllBy();
}
