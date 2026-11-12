package org.example.backendlabb2.repositories;

import org.example.backendlabb2.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {
    List<User> findAllBy();
}
