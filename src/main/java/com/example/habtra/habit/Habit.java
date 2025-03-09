package com.example.habtra.habit;

import com.example.habtra.habitEntry.HabitEntry;
import com.example.habtra.types.Enums;
import com.example.habtra.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "habits")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Habit {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="habit")
    @JsonManagedReference
    private Set<HabitEntry> entries;

    @Column(name = "frequency")
    private Enums.FrequencyType frequency;

    @Column(name = "target")
    private int target;

    public Habit() { }

    public Habit(String name, Set<HabitEntry> entries, User user, Enums.FrequencyType frequency, int target) {
        this.name = name;
        this.entries = entries;
        this.user = user;
        this.frequency = frequency;
        this.target = target;
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

    public Enums.FrequencyType getFrequency() {
        return frequency;
    }

    public void setFrequency(Enums.FrequencyType frequency) {
        this.frequency = frequency;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}