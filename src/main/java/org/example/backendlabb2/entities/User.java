package org.example.backendlabb2.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;

    @OneToMany(mappedBy = "author")
    private List<Ticket> authoredTickets = new ArrayList<>();

    @OneToMany(mappedBy = "assignee")
    private List<Ticket> assignedTickets = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> authoredComments = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Ticket> getAuthoredTickets() {
        return authoredTickets;
    }

    public void setAuthoredTickets(List<Ticket> authoredTickets) {
        this.authoredTickets = authoredTickets;
    }

    public List<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(List<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    public List<Comment> getAuthoredComments() {
        return authoredComments;
    }

    public void setAuthoredComments(List<Comment> authoredComments) {
        this.authoredComments = authoredComments;
    }
}
