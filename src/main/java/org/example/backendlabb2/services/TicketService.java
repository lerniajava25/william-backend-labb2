package org.example.backendlabb2.services;

import jakarta.transaction.Transactional;
import org.example.backendlabb2.entities.Role;
import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.entities.TicketStatus;
import org.example.backendlabb2.entities.User;
import org.example.backendlabb2.repositories.TicketRepository;
import org.example.backendlabb2.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAllBy();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket findAccessibleTicket(Long id, String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return findAccessibleTicket(id, user);
    }

    public Ticket findAccessibleTicket(Long id, User user) {
        if(!user.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var ticket = ticketRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(user.getRole() == Role.USER && !ticket.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ticket;
    }

    public Ticket addTicket(String title, String description, String ticketAuthorName) {
        if(title == null || title.isBlank() || description == null || description.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var ticketAuthor = userRepository.findByUsername(ticketAuthorName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(ticketAuthor.getRole() != Role.USER || !ticketAuthor.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setStatus(TicketStatus.NEW);
        ticket.setAuthor(ticketAuthor);
        return save(ticket);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
