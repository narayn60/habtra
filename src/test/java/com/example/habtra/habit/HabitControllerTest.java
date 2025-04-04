package com.example.habtra.habit;

import com.example.habtra.habit.dtos.HabitDto;
import com.example.habtra.habit.dtos.PostDto;
import com.example.habtra.types.Enums;
import com.example.habtra.user.CustomUserDetails;
import com.example.habtra.user.User;
import com.example.habtra.user.UserDetailsServiceImpl;
import com.example.habtra.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.mockito.Mockito.when;

@WebMvcTest(HabitController.class)
class HabitControllerTest {

    @MockBean
    private HabitService habitService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

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
    public void testHandleNewHabitRequest() throws Exception {
        HabitController controller = new HabitController(this.habitService, this.userService);
        PostDto habitDto = new PostDto("guitar", Enums.FrequencyType.Daily.toString(), 60);

        when(userService.getById(customUserDetails.getId()))
                .thenReturn(this.user);

        when(habitService.add(ArgumentMatchers.any(Habit.class)))
                .thenReturn(new Habit("guitar", Collections.emptySet(), this.user, Enums.FrequencyType.Daily, 10 ));

        HabitDto habitDto1 = controller.newHabit(this.customUserDetails, habitDto);
        Assertions.assertNotNull(habitDto1);
        Assertions.assertEquals("guitar", habitDto1.name());;
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void testHandleAllRequest() {
        HabitController controller = new HabitController(this.habitService, this.userService);

        List<Habit> habits = Arrays.asList(
                new Habit("guitar", Collections.emptySet(), this.user, Enums.FrequencyType.Daily, 10),
                new Habit("gym", Collections.emptySet(), this.user, Enums.FrequencyType.Daily, 10)
        );

        when(habitService.getAllHabits(this.customUserDetails.getId()))
                .thenReturn(habits);

        List<HabitDto> habitDtos = controller.all(this.customUserDetails, Optional.empty());
        Assertions.assertEquals(2, habitDtos.size());
        Assertions.assertArrayEquals(new String[]{"guitar", "gym"}, habitDtos.stream().map(HabitDto::name).toArray());;
    }

    @Test
    public void testHandleGetRequest() {
        HabitController controller = new HabitController(this.habitService, this.userService);
        UUID habitId = UUID.randomUUID();
        Habit habit = new Habit("guitar", Collections.emptySet(), this.user, Enums.FrequencyType.Daily, 10 );

        when(habitService.get(habitId))
                .thenReturn(habit);

        HabitDto habitDto = controller.get(habitId, Optional.empty());
        Assertions.assertEquals("guitar", habitDto.name());
        Assertions.assertEquals(Enums.FrequencyType.Daily, habitDto.frequency());
    }
}