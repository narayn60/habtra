package com.example.habtra.habitEntry;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Component
public class HabitEntryService {

    private final HabitEntryRepository repository;

    public HabitEntryService(HabitEntryRepository repository) {
        this.repository = repository;
    }

    public HabitEntry create(HabitEntry habitEntry) {
        return repository.save(habitEntry);
    }

    public List<HabitEntry> getAll(UUID userId) {
        return repository.findAllHabitEntriesByUserId(userId);
    }

    public List<HabitEntry> getForDay(UUID userId, Timestamp day) {
        return repository.findAllByDay(userId, day);
    }

    public HabitEntry getById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("HabitEntry not found")
        );
    }
}