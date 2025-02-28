package com.example.habtra.habit.dtos;

import com.example.habtra.habit.Habit;
import com.example.habtra.habitEntry.dtos.HabitEntryDto;
import com.example.habtra.types.Enums;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record HabitDto(
        UUID id,
        String name,
        Enums.FrequencyType frequency,
        int target,
        List<HabitEntryDto> entries
) implements Serializable {
    public static HabitDto fromEntity(Habit habit) {
        List<HabitEntryDto> entries = habit.getEntries().stream().map(HabitEntryDto::fromEntity).toList();
        return new HabitDto(
                habit.getId(),
                habit.getName(),
                habit.getFrequency(),
                habit.getTarget(),
                entries
        );
    }
}