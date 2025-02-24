package com.example.habtra.habit;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class HabitService {

    private final HabitRepository repository;

    HabitService(HabitRepository repository) {
        this.repository = repository;
    }

    public Habit get(UUID habitId) {
        return this.repository.findById(habitId);
    }

    public Habit add(Habit newHabit) {
        return repository.save(newHabit);
    }

    public List<Habit> getAllHabits(UUID userId) {
        return repository.findAllByUserId(userId).orElse(new ArrayList<>());
    }

}