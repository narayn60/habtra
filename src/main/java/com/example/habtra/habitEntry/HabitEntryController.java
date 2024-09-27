package com.example.habtra.habitEntry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
