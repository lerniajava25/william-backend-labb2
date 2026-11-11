package org.example.backendlabb2.controllers;

import org.example.backendlabb2.dtos.TicketDTO;
import org.example.backendlabb2.dtos.TicketRequestDTO;
import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketDTO> findAll() {
        return ticketService.findAll().stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getAuthor(),
                        ticket.getAssignee(),
                        ticket.getCreatedAt(),
                        ticket.getModifiedAt()
                )).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> findById(@PathVariable Long id) {
        return ticketService.findById(id)
                .map(ticket -> ResponseEntity.ok(new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getDescription(),
                        ticket.getStatus(),
                        ticket.getAuthor(),
                        ticket.getAssignee(),
                        ticket.getCreatedAt(),
                        ticket.getModifiedAt())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TicketDTO save(@RequestBody TicketRequestDTO req) {
        Ticket ticket = new Ticket();
        ticket.setTitle(req.title());
        ticket.setDescription(req.description());
        ticket.setStatus(req.status());
        ticket.setAuthor(req.author());
        ticket.setAssignee(req.assignee());
        Ticket saved = ticketService.save(ticket);

        return new TicketDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus(),
                saved.getAuthor(),
                saved.getAssignee(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> update(@PathVariable Long id, @RequestBody TicketRequestDTO req) {
        Ticket ticket = ticketService.findById(id).orElse(null);
        if(ticket == null) return ResponseEntity.notFound().build();

        ticket.setTitle(req.title());
        ticket.setDescription(req.description());
        ticket.setStatus(req.status());
        ticket.setAuthor(req.author());
        ticket.setAssignee(req.assignee());
        Ticket updated = ticketService.save(ticket);

        return ResponseEntity.ok(new TicketDTO(
                updated.getId(),
                updated.getTitle(),
                updated.getDescription(),
                updated.getStatus(),
                updated.getAuthor(),
                updated.getAssignee(),
                updated.getCreatedAt(),
                updated.getModifiedAt()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
