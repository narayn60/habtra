package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "habit_entry")
public class HabitEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "habit_id", insertable = false, updatable = false)
    private Habit habit;

    public HabitEntry() {}

    public HabitEntry(Habit habit) {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}