package com.example.habtra.habit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HabitController {

    private final HabitRepository repository;

    HabitController(HabitRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/habits")
    List<Habit> all() {
        return repository.findAll();
    }
}
