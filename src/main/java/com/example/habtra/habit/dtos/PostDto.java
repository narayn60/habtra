package com.example.habtra.habit.dtos;

import com.example.habtra.habit.Habit;
import com.example.habtra.types.Enums;
import com.example.habtra.user.User;

import java.security.Timestamp;
import java.util.Collections;

public record PostDto(
        String habit,
        String frequency,
        int target
) {
    public static Habit toEntity(PostDto postDto, User user) {
        return new Habit(
                postDto.habit(),
                Collections.emptySet(),
                user,
                Enums.FrequencyType.valueOf(postDto.frequency()),
                postDto.target
        );
    }
}