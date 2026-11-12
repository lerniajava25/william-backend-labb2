package org.example.backendlabb2.services;

import jakarta.transaction.Transactional;
import org.example.backendlabb2.entities.*;
import org.example.backendlabb2.repositories.CommentRepository;
import org.example.backendlabb2.repositories.TicketRepository;
import org.example.backendlabb2.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DataSeedingService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    DataSeedingService(UserRepository userRepository, TicketRepository ticketRepository, CommentRepository commentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedData() {
        User user = new User();
        user.setUsername("user");
        user.setPasswordHash(passwordEncoder.encode("password"));
        user.setActive(true);
        user.setRole(Role.USER);
        userRepository.save(user);

        User agent = new User();
        agent.setUsername("agent");
        agent.setPasswordHash(passwordEncoder.encode("password"));
        agent.setActive(true);
        agent.setRole(Role.AGENT);
        userRepository.save(agent);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("password"));
        admin.setActive(true);
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
