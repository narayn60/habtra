package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import com.example.habtra.habit.HabitService;
import com.example.habtra.habitEntry.dtos.HabitEntryDto;
import com.example.habtra.habitEntry.dtos.PostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/habitEntries")
public class HabitEntryController {
    private final HabitEntryService service;
    private final HabitService habitService;

    public HabitEntryController(HabitEntryService service, HabitService habitService) {
        this.service = service;
        this.habitService = habitService;
    }


    @GetMapping()
    public List<HabitEntryDto> getHabitEntries() {
        List<HabitEntry> habitEntries = service.getAll();
        return habitEntries.stream().map(HabitEntryDto::fromEntity).toList();
    }

    @GetMapping(value = "/{id}")
    public HabitEntryDto getHabitEntry(@PathVariable UUID id) {
        // TODO: Catch case where UUID is not valid
        HabitEntry habitEntry = service.getById(id);
        return HabitEntryDto.fromEntity(habitEntry);
    }

    @PostMapping()
    public ResponseEntity<HabitEntry> createHabitEntry(@RequestBody PostDto postDto) {
        HabitEntry entry = toEntity(postDto);
        HabitEntry addedHabitEntry = service.create(entry);
        return new ResponseEntity<>(addedHabitEntry, HttpStatus.CREATED);
    }

//    @PutMapping(value = "/habitEntries/{id}")
//    public HabitEntry putHabitEntry(@RequestBody HabitEntry newHabitEntry, @PathVariable UUID id) {
//        return service.get(id)
//                .map(habitEntry -> {
//                    habitEntry.setEndTime(newHabitEntry.getEndTime());
//                    return service.create(habitEntry);
//                })
//                .orElseGet(() -> service.create(newHabitEntry));
//    }

    private HabitEntry toEntity(PostDto postDto) {
        // TODO: Move this and maybe move to class
        Habit habit = this.habitService.get(postDto.habitId());

        return new HabitEntry(habit, postDto.startTime(), postDto.endTime());
    }
}