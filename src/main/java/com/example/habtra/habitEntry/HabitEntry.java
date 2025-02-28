package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "habit_entries")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class HabitEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @JoinColumn(name = "habit_id", updatable = false)
    @JsonBackReference
    @JsonProperty("test")
    @ManyToOne(optional = false)
    private Habit habit;

    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;


    @Column(name = "note")
    private String note;

    public HabitEntry(Habit habit, Timestamp startTime, Timestamp endTime, String note) {
        this.habit = habit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
    }

    public Habit getHabit() {
        return habit;
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

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }
}