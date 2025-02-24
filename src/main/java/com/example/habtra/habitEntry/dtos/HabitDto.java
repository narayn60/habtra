package com.example.habtra.habitEntry.dtos;

import com.example.habtra.habit.Habit;

import java.io.Serializable;
import java.util.UUID;

public record HabitDto(UUID id, String name) implements Serializable {
    public static HabitDto fromEntity(Habit habit) {
        return new HabitDto(habit.getId(), habit.getName());
    }
}