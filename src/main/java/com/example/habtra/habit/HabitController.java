package com.example.habtra.habit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    List<Habit> all() {
        return habitService.getAllHabits();
    }

    @PostMapping
    public ResponseEntity<Habit> newHabit(@RequestBody Habit newHabit) {
        // TODO: Ensure we don't pass id and only generate one
        Habit addedHabit = habitService.add(newHabit);
        return new ResponseEntity<>(addedHabit, HttpStatus.CREATED);
    }
}
