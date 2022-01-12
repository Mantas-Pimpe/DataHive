package com.datahive.controllers;

import com.datahive.entities.UserEntity;
import com.datahive.repositories.UserRestRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserRestRepository repository;

    public UserController(UserRestRepository repository) {
        this.repository = repository;
    }

    @PutMapping("/user")
    UserEntity saveComment(@RequestBody UserEntity user) {
        return repository.save(new UserEntity(user.getUsername()));
    }

    @DeleteMapping("/user/{id}")
    void deleteComment(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
