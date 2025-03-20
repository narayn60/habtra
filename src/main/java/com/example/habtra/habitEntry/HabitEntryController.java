package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import com.example.habtra.habit.HabitService;
import com.example.habtra.habitEntry.dtos.HabitEntryDto;
import com.example.habtra.habitEntry.dtos.PostDto;
import com.example.habtra.user.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public List<HabitEntryDto> getAllHabitEntries(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam Optional<String> day
    ) {
        UUID userId = user.getId();
        List<HabitEntry> habitEntries;

        // TODO: Improve this
        if (day.isPresent()) {
            try {
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = formatter.parse(day.get());
                Timestamp dayTimeStamp = new Timestamp(date.getTime());
                habitEntries = service.getForDay(userId, dayTimeStamp);
            } catch (ParseException e){
                System.out.println("Exception :" + e);
                return null;
            }
        } else {
            habitEntries = service.getAll(userId);
        }
        return habitEntries.stream().map(HabitEntryDto::fromEntity).toList();
    }

    @GetMapping(value = "/{id}")
    public HabitEntryDto getHabitEntry(@PathVariable UUID id) {
        // TODO: Catch case where UUID is not valid
        HabitEntry habitEntry = service.getById(id);
        return HabitEntryDto.fromEntity(habitEntry);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public HabitEntryDto createHabitEntry(@RequestBody PostDto postDto) {
        HabitEntry entry = toEntity(postDto);
        HabitEntry createdEntry = service.create(entry);
        return HabitEntryDto.fromEntity(createdEntry);
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
        Habit habit = this.habitService.get(postDto.habitId());
        return PostDto.toEntity(postDto, habit);
    }
}