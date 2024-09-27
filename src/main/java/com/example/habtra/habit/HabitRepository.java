package com.example.habtra.habit;

import org.springframework.data.jpa.repository.JpaRepository;

interface HabitRepository extends JpaRepository<Habit, Long> {
}
