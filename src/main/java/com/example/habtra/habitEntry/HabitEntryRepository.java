package com.example.habtra.habitEntry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
public interface HabitEntryRepository extends JpaRepository<HabitEntry, UUID> {
    List<HabitEntry> findByHabitId(UUID id);
}