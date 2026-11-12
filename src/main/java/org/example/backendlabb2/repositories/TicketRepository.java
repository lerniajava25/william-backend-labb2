package org.example.backendlabb2.repositories;

import org.example.backendlabb2.entities.Ticket;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

@NullMarked
public interface TicketRepository extends ListCrudRepository<Ticket, Long> {
    @EntityGraph(attributePaths = {"author", "assignee"})
    List<Ticket> findAllBy();

    @Override
    @EntityGraph(attributePaths = {"author", "assignee"})
    Optional<Ticket> findById(Long id);
}
