package com.example.habtra.habit;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitService {

    private final HabitRepository repository;

    HabitService(HabitRepository repository) {
        this.repository = repository;
    }

    public Habit add(Habit newHabit) {
        return repository.save(newHabit);
    }

    public List<Habit> getAllHabits() {
        return repository.findAll();
    }
}
