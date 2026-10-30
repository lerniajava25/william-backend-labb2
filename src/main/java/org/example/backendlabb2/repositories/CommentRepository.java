package org.example.backendlabb2.repositories;

import org.example.backendlabb2.entities.Comment;
import org.springframework.data.repository.ListCrudRepository;

public interface CommentRepository extends ListCrudRepository<Comment, Long> {

}
