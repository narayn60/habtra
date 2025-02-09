package com.example.habtra.habit;

import com.example.habtra.user.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    List<Habit> all(@AuthenticationPrincipal CustomUserDetails user) {
        UUID id = user.getId();
        return habitService.getAllHabits(id);
    }

    @PostMapping
    public ResponseEntity<Habit> newHabit(@RequestBody Habit newHabit) {
        // TODO: Ensure we don't pass id and only generate one
        Habit addedHabit = habitService.add(newHabit);
        return new ResponseEntity<>(addedHabit, HttpStatus.CREATED);
    }
}
