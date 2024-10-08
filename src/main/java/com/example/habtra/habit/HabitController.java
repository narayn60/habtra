package com.example.habtra.habit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HabitController {

    private final HabitRepository repository;

    HabitController(HabitRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/habits")
    List<Habit> all() {
        return repository.findAll();
    }

    @PostMapping(value = "/habits")
    Habit newHabit(@RequestBody Habit newHabit) {
        // TODO: Ensure we don't pass id and only generate one
        return repository.save(newHabit);
    }
}
