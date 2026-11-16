package org.example.backendlabb2.controllers;

import org.example.backendlabb2.entities.Comment;
import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.services.CommentService;
import org.example.backendlabb2.services.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
        return "tickets/tickets";
    }

    @GetMapping("/{id}")
    public String ticketDetails(@PathVariable Long id, Model model, Principal principal) {
        Ticket ticket = ticketService.findAccessibleTicket(id, principal.getName());
        List<Comment> comments = commentService.findByTicket(id);

        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);
        return "tickets/details";
    }

    @PostMapping
    public String addTicket(@RequestParam String title, @RequestParam String description, Principal principal) {
        var ticket = ticketService.addTicket(title, description, principal.getName());
        return "redirect:/tickets/" + ticket.getId();
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam String commentText, Principal principal) {
        commentService.addComment(id, principal.getName(), commentText);
        return "redirect:/tickets/" + id;
    }
}
