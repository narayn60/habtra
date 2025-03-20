package com.example.habtra.habit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class HabitService {

    private final HabitRepository repository;

    @PersistenceContext
    private EntityManager em;

    HabitService(HabitRepository repository) {
        this.repository = repository;
    }

    public Habit get(UUID habitId) { return this.repository.findById(habitId); }

    public Habit get(UUID habitId, Timestamp day) {
        Session session = em.unwrap(Session.class);
        session.enableFilter("dateFilter").setParameter("date", day);

        return this.repository.findById(habitId);
    }

    public Habit add(Habit newHabit) {
        return repository.save(newHabit);
    }

    public List<Habit> getAllHabits(UUID userId) {
        return repository.findAllByUserId(userId).orElse(new ArrayList<>());
    }

    public List<Habit> getAllHabits(UUID userId, Timestamp day) {
        Session session = em.unwrap(Session.class);
        session.enableFilter("dateFilter").setParameter("date", day);

        return repository.findAllByUserId(userId).orElse(new ArrayList<>());
    }

}