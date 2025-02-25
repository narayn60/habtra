package com.example.habtra.habit.dtos;

import com.example.habtra.habitEntry.HabitEntry;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * DTO for {@link HabitEntry}
 */
public record HabitEntryDto(UUID id, Timestamp startTime, Timestamp endTime) implements Serializable {
    public static HabitEntryDto fromEntity(HabitEntry habitEntry) {
        return new HabitEntryDto(habitEntry.getId(), habitEntry.getStartTime(), habitEntry.getEndTime());
    }
}