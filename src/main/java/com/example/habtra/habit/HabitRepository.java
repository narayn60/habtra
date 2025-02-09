package com.example.habtra.habit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Optional<List<Habit>> findAllByUserId(UUID userId);
}
