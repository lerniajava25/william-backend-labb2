package org.example.backendlabb2.controllers;

import org.example.backendlabb2.entities.*;
import org.example.backendlabb2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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
        model.addAttribute("roles", Role.values());
        return "users/users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        var user = userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "users/edit";
    }

    @PostMapping("/new")
    public String createUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam Role role,
                             @RequestParam(defaultValue = "false") boolean active,
                             Principal principal) {
        userService.createUser(username, password, role, active, principal.getName());
        return "redirect:/users";
    }

    @PostMapping("/{id}/update-role")
    public String updateRole(@PathVariable Long id, @RequestParam Role role, Principal principal) {
        var user = userService.updateRole(id, role, principal.getName());
        return "redirect:/users/" + user.getId();
    }

    @PostMapping("/{id}/update-status")
    public String updateStatus(@PathVariable Long id, @RequestParam boolean active, Principal principal) {
        var user = userService.updateStatus(id, active, principal.getName());
        return "redirect:/users/" + user.getId();
    }
}
