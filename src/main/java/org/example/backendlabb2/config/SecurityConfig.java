package org.example.backendlabb2.config;

import org.example.backendlabb2.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/error", "/css/**", "/favicon.ico").permitAll()
                .requestMatchers("/api/**", "/tickets", "/tickets/**").authenticated()
                .anyRequest().denyAll());
        http.formLogin(form -> form.defaultSuccessUrl("/tickets", true));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPasswordHash())
                        .roles(user.getRole().name())
                        .disabled(!user.isActive())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
