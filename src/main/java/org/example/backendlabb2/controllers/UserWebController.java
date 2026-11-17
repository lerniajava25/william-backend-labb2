package org.example.backendlabb2.controllers;

import org.example.backendlabb2.entities.User;
import org.example.backendlabb2.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserWebController {
    private final UserService userService;

    UserWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listTickets(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/users";
    }
}
