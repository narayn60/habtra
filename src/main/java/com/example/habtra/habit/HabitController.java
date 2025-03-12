package com.example.habtra.habit;

import com.example.habtra.habit.dtos.HabitDto;
import com.example.habtra.habit.dtos.PostDto;
import com.example.habtra.user.CustomUserDetails;
import com.example.habtra.user.User;
import com.example.habtra.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
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
    List<HabitDto> all(@AuthenticationPrincipal CustomUserDetails user) {
        UUID id = user.getId();
        return habitService.getAllHabits(id).stream()
                .map(HabitDto::fromEntity)
                .toList();
    }

    @GetMapping(value = "/{id}")
    public HabitDto get(@PathVariable UUID id) {
        // TODO: Limit to ensure we only get if user owns
        return HabitDto.fromEntity(this.habitService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitDto newHabit(@AuthenticationPrincipal CustomUserDetails user, @RequestBody PostDto postDto) throws ParseException {
        User u = this.userService.getById(user.getId());
        Habit newHabit = PostDto.toEntity(postDto, u);
        return HabitDto.fromEntity(habitService.add(newHabit));
    }
}