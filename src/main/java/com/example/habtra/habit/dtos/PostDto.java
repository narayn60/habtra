package com.example.habtra.habit.dtos;

import com.example.habtra.habit.Habit;
import com.example.habtra.user.User;

import java.util.Collections;

public record PostDto(String habit) {
    public static Habit toEntity(PostDto postDto, User user) {
        return new Habit(postDto.habit(), Collections.emptySet(), user);
    }
}