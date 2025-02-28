package com.example.habtra.habitEntry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitEntryServiceTest {

    @Mock
    private HabitEntryRepository repository;

    @InjectMocks
    private HabitEntryService service;

    @Test
    public void testGetById() {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 6000);
        UUID id = UUID.randomUUID();

        // Given
        HabitEntry habitEntry = new HabitEntryBuilder().createHabitEntry();
        habitEntry.setId(id);
        habitEntry.setStartTime(startTime);
        habitEntry.setEndTime(endTime);
        when(repository.findById(id)).thenReturn(Optional.of(habitEntry));

        // When
        HabitEntry foundHabitEntry = service.getById(id);

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
        assertThrows(RuntimeException.class, () -> service.getById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testCreate() {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 6000);

        // Given
        HabitEntry habitEntry = new HabitEntryBuilder().createHabitEntry();
        habitEntry.setStartTime(startTime);
        habitEntry.setEndTime(endTime);
        when(repository.save(any(HabitEntry.class))).thenReturn(habitEntry);

        // When
        HabitEntry savedHabitEntry = service.create(habitEntry);

        // Then
        assertNotNull(savedHabitEntry);
        assertEquals(startTime, savedHabitEntry.getStartTime());
        verify(repository, times(1)).save(habitEntry);
    }

    @Test
    public void testGetAll() {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + 6000);
        List<UUID> ids = new ArrayList<UUID>() {{
            add(UUID.randomUUID());
            add(UUID.randomUUID());
        }};

        // Given
        List<HabitEntry> habitEntries = new ArrayList<HabitEntry>();

        for (int i = 0; i < 2; i++) {
            HabitEntry habitEntry = new HabitEntryBuilder().createHabitEntry();
            habitEntry.setId(ids.get(i));
            habitEntry.setStartTime(startTime);
            habitEntry.setEndTime(endTime);
            habitEntries.add(habitEntry);
        }

        when(repository.findAll()).thenReturn(habitEntries);

        // When
        List<HabitEntry> foundEntries = service.getAll();

        // Then
        assertNotNull(foundEntries);
        assertEquals(ids.get(0), foundEntries.get(0).getId());
        assertEquals(2, foundEntries.size());
        verify(repository, times(1)).findAll();
    }
}