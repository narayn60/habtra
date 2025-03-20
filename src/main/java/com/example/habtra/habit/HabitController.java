package com.example.habtra.habit;

import com.example.habtra.habit.dtos.HabitDto;
import com.example.habtra.habit.dtos.PostDto;
import com.example.habtra.user.CustomUserDetails;
import com.example.habtra.user.User;
import com.example.habtra.user.UserService;
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
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;
    private final UserService userService;

    HabitController(HabitService habitService, UserService userService) {
        this.habitService = habitService;
        this.userService = userService;
    }

    @GetMapping
    public List<HabitDto> all(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam Optional<String> day
    ) {
        UUID id = user.getId();
        List<Habit> habits;

        if (day.isPresent()) {
            try {
                Timestamp dayTimeStamp = this.formatDay(day.get());
                habits = habitService.getAllHabits(id, dayTimeStamp);
            } catch (ParseException e){
                System.out.println("Exception :" + e);
                return null;
            }
        } else {
            habits = habitService.getAllHabits(id);
        }

        return habits.stream()
                .map(HabitDto::fromEntity)
                .toList();
    }

    @GetMapping(value = "/{id}")
    public HabitDto get(
            @PathVariable UUID id,
            @RequestParam Optional<String> day
    ) {
        Habit result;

        // TODO: Limit to ensure we only get if user owns
        if (day.isPresent()) {
            try {
                Timestamp dayTimeStamp = this.formatDay(day.get());
                result = this.habitService.get(id, dayTimeStamp);
            } catch (ParseException e){
                System.out.println("Exception :" + e);
                return null;
            }
        } else {
            result = this.habitService.get(id);
        }
        return HabitDto.fromEntity(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitDto newHabit(@AuthenticationPrincipal CustomUserDetails user, @RequestBody PostDto postDto) throws ParseException {
        User u = this.userService.getById(user.getId());
        Habit newHabit = PostDto.toEntity(postDto, u);
        return HabitDto.fromEntity(habitService.add(newHabit));
    }

    private Timestamp formatDay(String day) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(day);
        return new Timestamp(date.getTime());
    }
}