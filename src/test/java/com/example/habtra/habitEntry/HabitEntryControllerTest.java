package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import com.example.habtra.habit.HabitService;
import com.example.habtra.habitEntry.dtos.HabitEntryDto;
import com.example.habtra.habitEntry.dtos.PostDto;
import com.example.habtra.types.Enums;
import com.example.habtra.user.CustomUserDetails;
import com.example.habtra.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(HabitEntryController.class)
class HabitEntryControllerTest {

    @MockBean
    private HabitEntryService habitEntryService;

    @MockBean
    private HabitService habitService;

    private CustomUserDetails customUserDetails;
    private User user;

    @BeforeEach
    void setUp() {
        UUID uuid = UUID.randomUUID();
        this.customUserDetails = new CustomUserDetails("user", "password", uuid,  Collections.emptyList());
        this.user = new User("username", "password", "user@email.com");
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void getHabitEntries() {
        HabitEntry habitEntry = habitEntry();

        HabitEntryController controller = new HabitEntryController(habitEntryService, habitService);

        when(habitEntryService.getAll(this.customUserDetails.getId()))
                .thenReturn(Collections.singletonList(habitEntry));

        List<HabitEntryDto> habitEntries = controller.getHabitEntries(this.customUserDetails);

        Assertions.assertEquals(1, habitEntries.size());
        // TODO: Improve assertions
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void getHabitEntry() {
        HabitEntryController controller = new HabitEntryController(habitEntryService, habitService);

        HabitEntry habitEntry = habitEntry();
        UUID entryId = habitEntry.getId();

        // Mocks
        when(habitEntryService.getById(entryId))
                .thenReturn(habitEntry);

        // Query the controller
        HabitEntryDto habitEntryDto = controller.getHabitEntry(entryId);

        // Assertions
        Assertions.assertEquals(entryId, habitEntryDto.id());
        Assertions.assertEquals("note", habitEntryDto.note());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void createHabitEntry() {
        HabitEntryController controller = new HabitEntryController(habitEntryService, habitService);
        UUID habitId = UUID.randomUUID();

        // Create Habit
        Habit habit = new Habit("test_habit", null, this.user, Enums.FrequencyType.Daily, 1);
        habit.setId(habitId);

        // Create Habit Entry
        UUID habitEntryId = UUID.randomUUID();
        HabitEntry habitEntry = new HabitEntryBuilder()
                .setHabit(habit)
                .setNote("note")
                .createHabitEntry();
        habitEntry.setId(habitEntryId);

        PostDto postDto = new PostDto(
                habitId,
                habitEntry.getStartTime(),
                habitEntry.getEndTime(),
                habitEntry.getNote()
        );

        // Mocks
        when(habitService.get(habitId))
                .thenReturn(habit);

        when(habitEntryService.create(ArgumentMatchers.any(HabitEntry.class)))
                .thenReturn(habitEntry);

        // Query the controller
        HabitEntryDto habitEntryDto = controller.createHabitEntry(postDto);

        // Assertions
        Assertions.assertEquals(habitEntryId, habitEntryDto.id());
        Assertions.assertEquals("note", habitEntryDto.note());
    }

    // Helper functions
    private HabitEntry habitEntry() {
        // Create Habit
        Habit habit = new Habit("test_habit", null, this.user, Enums.FrequencyType.Daily, 1);

        // Create Habit Entry
        UUID id = UUID.randomUUID();
        HabitEntry habitEntry = new HabitEntryBuilder()
                .setHabit(habit)
                .setNote("note")
                .createHabitEntry();
        habitEntry.setId(id);

        return habitEntry;
    }
}