package com.example.habtra.habitEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitEntryServiceTest {

    @Mock
    private HabitEntryRepository repository;

    private HabitEntryService service;

    @BeforeEach
    void setUp() {
        service = new HabitEntryService(repository);
    }

    @Test
    public void testGetById() {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 6000);
        UUID id = UUID.randomUUID();

        HabitEntry habitEntry = new HabitEntryBuilder().createHabitEntry();
        habitEntry.setId(id);
        habitEntry.setStartTime(startTime);
        habitEntry.setEndTime(endTime);

        // Given
        when(repository.findById(id)).thenReturn(Optional.of(habitEntry));

        // When
        HabitEntry foundHabitEntry = this.service.getById(id);

        // Then
        assertNotNull(foundHabitEntry);
        assertEquals(startTime, foundHabitEntry.getStartTime());
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testGetById_NotFound() {
        UUID id = UUID.randomUUID();

        // Given
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> this.service.getById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testCreate() {
        // Given
        HabitEntry habitEntry = createHabitEntry(UUID.randomUUID());
        when(repository.save(any(HabitEntry.class))).thenReturn(habitEntry);

        // When
        HabitEntry savedHabitEntry = this.service.create(habitEntry);

        // Then
        assertNotNull(savedHabitEntry);
        assertEquals(habitEntry.getStartTime(), savedHabitEntry.getStartTime());
        verify(repository, times(1)).save(habitEntry);
    }

    @Test
    public void testGetAll() {
        UUID[] ids = new UUID[]{UUID.randomUUID(), UUID.randomUUID()};
        UUID userId = UUID.randomUUID();

        // Given
        List<HabitEntry> habitEntries = Arrays.stream(ids)
                .map(HabitEntryServiceTest::createHabitEntry)
                .toList();

        when(this.repository.findAllHabitEntriesByUserId(eq(userId)))
                .thenReturn(habitEntries);

        // When
        List<HabitEntry> foundEntries = this.service.getAll(userId);

        // Then
        assertNotNull(foundEntries);
        assertEquals(Set.of(ids), foundEntries.stream().map(HabitEntry::getId).collect(Collectors.toSet()));
        assertEquals(2, foundEntries.size());
        verify(repository, times(1)).findAllHabitEntriesByUserId(eq(userId));
    }

    public static HabitEntry createHabitEntry(UUID id) {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 6000);

        HabitEntry habitEntry = new HabitEntryBuilder().createHabitEntry();
        habitEntry.setId(id);
        habitEntry.setStartTime(startTime);
        habitEntry.setEndTime(endTime);
        return habitEntry;
    }
}