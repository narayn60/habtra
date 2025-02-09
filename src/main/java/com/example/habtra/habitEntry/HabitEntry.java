package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "habit_entries")
public class HabitEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "habit_id", updatable = false)
    @JsonBackReference
    private Habit habit;
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;

    public HabitEntry(Habit habit, Timestamp startTime, Timestamp endTime) {
        this.habit = habit;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getHabit() {
        return habit.getId();
    }

    public HabitEntry() {}

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}