package org.example.backendlabb2.controllers;

import org.example.backendlabb2.entities.Comment;
import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.services.CommentService;
import org.example.backendlabb2.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketWebController {
    private final TicketService ticketService;
    private final CommentService commentService;

    TicketWebController(TicketService ticketService, CommentService commentService) {
        this.ticketService = ticketService;
        this.commentService = commentService;
    }

    @GetMapping
    public String listTickets(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        model.addAttribute("ticket", new Ticket());
        return "tickets/tickets";
    }

    @GetMapping("/{id}")
    public String ticketDetails(@PathVariable Long id, Model model) {
        Optional<Ticket> ticket = ticketService.findById(id);
        List<Comment> comments = commentService.findByTicket(id);

        model.addAttribute("ticket", ticket.orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND))));
        model.addAttribute("comments", comments);
        return "tickets/details";
    }
}
