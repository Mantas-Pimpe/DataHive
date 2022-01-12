package com.datahive.controllers;

import com.datahive.entities.HistoryEntity;
import com.datahive.repositories.HistoryRestRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class HistoryController {
    private final HistoryRestRepository repository;

    public HistoryController(HistoryRestRepository repository) {
        this.repository = repository;
    }

    @PutMapping("/history")
    HistoryEntity saveComment(@RequestBody HistoryEntity historyEntry) {
        return repository.save(new HistoryEntity(historyEntry.getUrl(), historyEntry.getCmp(), historyEntry.getDate(), historyEntry.getUserId()));
    }

    @DeleteMapping("/history/{id}")
    void deleteComment(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
