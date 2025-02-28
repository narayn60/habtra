package com.example.habtra.habitEntry.dtos;

import com.example.habtra.habit.Habit;
import com.example.habtra.habitEntry.HabitEntry;

import java.sql.Timestamp;
import java.util.UUID;

public record PostDto(UUID habitId, Timestamp startTime, Timestamp endTime, String note) {
    public static HabitEntry toEntity(PostDto postDto, Habit habit) {
        return new HabitEntry(habit, postDto.startTime(), postDto.endTime(), postDto.note());
    }
}