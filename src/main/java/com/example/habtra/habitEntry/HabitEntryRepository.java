package com.example.habtra.habitEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
public interface HabitEntryRepository extends JpaRepository<HabitEntry, UUID> {
    List<HabitEntry> findByHabitId(UUID id);

    @Query("SELECT e FROM HabitEntry e JOIN e.habit h WHERE h.user.id = :userId")
    List<HabitEntry> findAllHabitEntriesByUserId(@Param("userId") UUID userId);

    @Query("SELECT e FROM HabitEntry e JOIN e.habit h WHERE h.user.id = :userId AND DATE(e.startTime) = :day")
    List<HabitEntry> findAllByDay(@Param("userId") UUID userId, @Param("day") Timestamp day);
}