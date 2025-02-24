package com.example.habtra.habitEntry.dtos;

import java.sql.Timestamp;
import java.util.UUID;

public record PostDto(UUID habitId, Timestamp startTime, Timestamp endTime) {
}