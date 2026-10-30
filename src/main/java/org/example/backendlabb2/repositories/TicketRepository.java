package org.example.backendlabb2.repositories;

import org.example.backendlabb2.entities.Ticket;
import org.springframework.data.repository.ListCrudRepository;

public interface TicketRepository extends ListCrudRepository<Ticket, Long> {

}
