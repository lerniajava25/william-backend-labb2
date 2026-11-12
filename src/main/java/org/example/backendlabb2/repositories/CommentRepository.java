package org.example.backendlabb2.repositories;

import org.example.backendlabb2.entities.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends ListCrudRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"author", "ticket"})
    List<Comment> findAllBy();

    @Override
    @EntityGraph(attributePaths = {"author", "ticket"})
    Optional<Comment> findById(Long id);
}
