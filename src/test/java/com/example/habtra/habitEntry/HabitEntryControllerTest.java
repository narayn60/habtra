package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import com.example.habtra.habit.HabitService;
import com.example.habtra.habitEntry.dtos.HabitEntryDto;
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
    void getHabitEntries() throws Exception {
        Habit habit = new Habit("test_habit", null, this.user, Enums.FrequencyType.Daily, 1);

        HabitEntry habitEntry = new HabitEntryBuilder().createHabitEntry();
        UUID id = UUID.randomUUID();
        habitEntry.setId(id);
        habitEntry.setHabit(habit);

        HabitEntryController controller = new HabitEntryController(habitEntryService, habitService);

        when(habitEntryService.getAll(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Collections.singletonList(habitEntry));

        List<HabitEntryDto> habitEntries = controller.getHabitEntries(this.customUserDetails);

        Assertions.assertEquals(1, habitEntries.size());
//        Assertions.assertArrayEquals(new String[]});
    }

    @Test
    void getHabitEntry() {
    }
}