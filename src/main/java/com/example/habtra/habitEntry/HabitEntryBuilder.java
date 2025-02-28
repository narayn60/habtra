package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;

import java.sql.Timestamp;

public class HabitEntryBuilder {
    private Habit habit;
    private Timestamp startTime;
    private Timestamp endTime;
    private String note;

    public HabitEntryBuilder setHabit(Habit habit) {
        this.habit = habit;
        return this;
    }

    public HabitEntryBuilder setStartTime(Timestamp startTime) {
        this.startTime = startTime;
        return this;
    }

    public HabitEntryBuilder setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        return this;
    }

    public HabitEntryBuilder setNote(String note) {
        this.note = note;
        return this;
    }

    public HabitEntry createHabitEntry() {
        return new HabitEntry(habit, startTime, endTime, note);
    }
}