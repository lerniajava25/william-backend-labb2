package org.example.backendlabb2.services;

import jakarta.transaction.Transactional;
import org.example.backendlabb2.entities.*;
import org.example.backendlabb2.repositories.CommentRepository;
import org.example.backendlabb2.repositories.TicketRepository;
import org.example.backendlabb2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DataSeedingService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    DataSeedingService(UserRepository userRepository, TicketRepository ticketRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    public void seedData() {
        User user = new User();
        user.setUsername("user");
        user.setPasswordHash("hash");
        user.setRole(Role.USER);
        userRepository.save(user);

        User agent = new User();
        agent.setUsername("agent");
        agent.setPasswordHash("hash");
        agent.setRole(Role.AGENT);
        userRepository.save(agent);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPasswordHash("hash");
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        Comment comment = new Comment();
        comment.setCommentText("This is a comment");
        comment.setAuthor(admin);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        Ticket ticket = new Ticket();
        ticket.setTitle("Example Ticket");
        ticket.setDescription("This is a ticket");
        ticket.setStatus(TicketStatus.NEW);
        ticket.setAuthor(user);
        ticket.setAssignee(agent);
        comment.setTicket(ticket);
        ticket.setComments(comments);

        commentRepository.save(comment);
        ticketRepository.save(ticket);
    }
}
