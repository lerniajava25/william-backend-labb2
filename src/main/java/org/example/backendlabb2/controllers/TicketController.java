package org.example.backendlabb2.controllers;

import jakarta.validation.Valid;
import org.example.backendlabb2.dtos.TicketDTO;
import org.example.backendlabb2.dtos.TicketRequestDTO;
import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.entities.User;
import org.example.backendlabb2.services.TicketService;
import org.example.backendlabb2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final UserService userService;

    TicketController(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping
    public List<TicketDTO> findAll() {
        return ticketService.findAll().stream()
                .map(TicketDTO::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> findById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ticket -> ResponseEntity.ok(TicketDTO.from(ticket)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TicketDTO save(@Valid  @RequestBody TicketRequestDTO req) {
        Ticket ticket = new Ticket();
        applyRequest(ticket, req);
        return TicketDTO.from(ticketService.save(ticket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> update(@PathVariable Long id, @Valid @RequestBody TicketRequestDTO req) {
        Ticket ticket = ticketService.findById(id).orElse(null);
        if(ticket == null) return ResponseEntity.notFound().build();

        applyRequest(ticket, req);
        return ResponseEntity.ok(TicketDTO.from(ticketService.save(ticket)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void applyRequest(Ticket ticket, TicketRequestDTO req) {
        ticket.setTitle(req.title());
        ticket.setDescription(req.description());
        ticket.setStatus(req.status());
        ticket.setAuthor(findUser(req.authorId()));
        ticket.setAssignee(req.assigneeId() == null ? null : findUser(req.assigneeId()));
    }

    private User findUser(Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User " + id + " does not exist"));
    }
}
