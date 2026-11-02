package org.example.backendlabb2;

import org.example.backendlabb2.entities.Role;
import org.example.backendlabb2.entities.User;
import org.example.backendlabb2.repositories.UserRepository;
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
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPasswordHash("hash");
                user.setRole(Role.ADMIN);
                userRepository.save(user);
            }
        };
    }
}
