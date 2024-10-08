package com.example.habtra.habitEntry;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class HabitEntryController {
    private final HabitEntryRepository repository;

    public HabitEntryController(HabitEntryRepository habitEntryRepository) {
        this.repository = habitEntryRepository;
    }

    @GetMapping(value = "/habitEntries")
    List<HabitEntry> getHabitEntries() {
        return repository.findAll();
    }

    @PostMapping(value = "/habitEntries")
    HabitEntry createHabitEntry(@RequestBody HabitEntry habitEntry) {
        return repository.save(habitEntry);
    }

    @PutMapping(value = "/habitEntries/{id}")
    HabitEntry putHabitEntry(@RequestBody HabitEntry newHabitEntry, @PathVariable UUID id) {
        return repository.findById(id)
                .map(habitEntry -> {
                    habitEntry.setEndTime(newHabitEntry.getEndTime());
                    return repository.save(habitEntry);
                })
                .orElseGet(() -> repository.save(newHabitEntry));
    }
}
