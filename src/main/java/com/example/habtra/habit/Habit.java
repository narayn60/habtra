package com.example.habtra.habit;

import com.example.habtra.habitEntry.HabitEntry;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="habit")
    private Set<HabitEntry> entries;

    public Habit() {}

    public Habit(String name, Set<HabitEntry> entries) {
        this.name = name;
        this.entries = entries;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<HabitEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<HabitEntry> entries) {
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
