package com.example.habtra.habit;

import com.example.habtra.user.CustomUserDetails;
import com.example.habtra.user.User;
import com.example.habtra.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collections;
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
    List<Habit> all(@AuthenticationPrincipal CustomUserDetails user) {
        UUID id = user.getId();
        return habitService.getAllHabits(id);
    }

    @PostMapping
    public ResponseEntity<Habit> newHabit(@AuthenticationPrincipal CustomUserDetails user, @RequestBody PostDto postDto) throws ParseException {
        // TODO: Ensure we don't pass id and only generate one
        Habit newHabit = toEntity(postDto, user.getId());
        Habit addedHabit = habitService.add(newHabit);
        return new ResponseEntity<>(addedHabit, HttpStatus.CREATED);
    }

    private Habit toEntity(PostDto postDto, UUID userId) throws ParseException {
        // TODO: Move this and maybe move to class
        User user = this.userService.getById(userId);
        return new Habit(postDto.getHabit(), Collections.emptySet(), user);
    }
}
