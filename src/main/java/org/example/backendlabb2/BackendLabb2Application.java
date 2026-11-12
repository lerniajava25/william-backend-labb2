package org.example.backendlabb2;

import org.example.backendlabb2.entities.Role;
import org.example.backendlabb2.entities.User;
import org.example.backendlabb2.repositories.CommentRepository;
import org.example.backendlabb2.repositories.TicketRepository;
import org.example.backendlabb2.repositories.UserRepository;
import org.example.backendlabb2.services.DataSeedingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendLabb2Application {

    public static void main(String[] args) {
        SpringApplication.run(BackendLabb2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, TicketRepository ticketRepository, CommentRepository commentRepository, DataSeedingService dataSeedingService) {
        return args -> {
            if (userRepository.count() == 0 && ticketRepository.count() == 0 && commentRepository.count() == 0) {
                dataSeedingService.seedData();
            }
        };
    }
}
