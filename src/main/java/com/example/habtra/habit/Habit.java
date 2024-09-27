package com.example.habtra.habit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Habit {

    private @Id @GeneratedValue Long id;
    private String name;

    public Habit() {}

    public Habit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
