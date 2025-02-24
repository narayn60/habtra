package com.example.habtra.habitEntry;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HabitEntryService {

    private final HabitEntryRepository repository;

    HabitEntryService(HabitEntryRepository repository) {
        this.repository = repository;
    }

    public HabitEntry add(HabitEntry habitEntry) {
        return repository.save(habitEntry);
    }

    public List<HabitEntry> getAll() {
        return repository.findAll();
    }

    public Optional<HabitEntry> get(UUID id) {
        return repository.findById(id);
    }
}