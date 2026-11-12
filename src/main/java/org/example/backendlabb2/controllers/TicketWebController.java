package org.example.backendlabb2.controllers;

import org.example.backendlabb2.entities.Ticket;
import org.example.backendlabb2.services.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketWebController {
    private final TicketService ticketService;

    TicketWebController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String listTickets(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);
        model.addAttribute("ticket", new Ticket());
        return "tickets";
    }
}
