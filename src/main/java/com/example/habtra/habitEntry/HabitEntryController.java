package com.example.habtra.habitEntry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class HabitEntryController {
    private final HabitEntryService service;

    public HabitEntryController(HabitEntryService service) {
        this.service = service;
    }

    @GetMapping(value = "/username")
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @GetMapping(value = "/habitEntries")
    List<HabitEntry> getHabitEntries() {
        return service.getAll();
    }

    @PostMapping(value = "/habitEntries")
    public ResponseEntity<HabitEntry> createHabitEntry(@RequestBody HabitEntry habitEntry) {
        HabitEntry addedHabitEntry = service.add(habitEntry);
        return new ResponseEntity<>(addedHabitEntry, HttpStatus.CREATED);
    }

    @PutMapping(value = "/habitEntries/{id}")
    HabitEntry putHabitEntry(@RequestBody HabitEntry newHabitEntry, @PathVariable UUID id) {
        return service.get(id)
                .map(habitEntry -> {
                    habitEntry.setEndTime(newHabitEntry.getEndTime());
                    return service.add(habitEntry);
                })
                .orElseGet(() -> service.add(newHabitEntry));
    }
}
