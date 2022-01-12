package com.datahive.controllers;

import com.datahive.entities.NotWorkingWebsiteEntity;
import com.datahive.repositories.NotWorkingWebsiteRestRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

@RestController
public class NotWorkingWebsiteController {
    private final NotWorkingWebsiteRestRepository repository;

    public NotWorkingWebsiteController(NotWorkingWebsiteRestRepository repository) {
        this.repository = repository;
    }

    @PutMapping("/not_working_website")
    NotWorkingWebsiteEntity saveComment(@RequestBody NotWorkingWebsiteEntity notWorkingWebsiteEntry) {
        return repository.save(new NotWorkingWebsiteEntity(notWorkingWebsiteEntry.getUrl(), notWorkingWebsiteEntry.getDate()));
    }

    @DeleteMapping("/not_working_website/{id}")
    void deleteComment(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
