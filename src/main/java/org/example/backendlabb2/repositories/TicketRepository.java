package org.example.backendlabb2.repositories;

import org.example.backendlabb2.entities.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TicketRepository extends ListCrudRepository<Ticket, Long> {
    @EntityGraph(value = "Ticket.detail")
    List<Ticket> findAllBy();
}
