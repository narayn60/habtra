package com.example.habtra.habit;

import com.example.habtra.habitEntry.HabitEntry;
import com.example.habtra.user.User;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "habits")
public class Habit {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="habit")
    private Set<HabitEntry> entries;

    public Habit() {}

    public Habit(String name, Set<HabitEntry> entries, User user) {
        this.name = name;
        this.entries = entries;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
