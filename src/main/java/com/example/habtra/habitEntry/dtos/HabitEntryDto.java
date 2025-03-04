package com.example.habtra.habitEntry.dtos;

import com.example.habtra.habitEntry.HabitEntry;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * DTO for {@link HabitEntry}
 */
public record HabitEntryDto(UUID id, Timestamp startTime, Timestamp endTime, String note,
                            HabitDto habit) implements Serializable {
    public static HabitEntryDto fromEntity(HabitEntry habitEntry) {
        return new HabitEntryDto(
                habitEntry.getId(),
                habitEntry.getStartTime(),
                habitEntry.getEndTime(),
                habitEntry.getNote(),
                HabitDto.fromEntity(habitEntry.getHabit())
        );
    }
}