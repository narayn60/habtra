package com.example.habtra.habit;

import com.example.habtra.habit.dtos.HabitDto;
import com.example.habtra.habit.dtos.PostDto;
import com.example.habtra.types.Enums;
import com.example.habtra.user.CustomUserDetails;
import com.example.habtra.user.User;
import com.example.habtra.user.UserDetailsServiceImpl;
import com.example.habtra.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(HabitController.class)
class HabitControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HabitService habitService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();
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
        PostDto habitDto = new PostDto("guitar", Enums.FrequencyType.Daily.toString(), 60);
        HabitController controller = new HabitController(this.habitService, this.userService);

        when(userService.getById(any()))
                .thenReturn(this.user);

        when(habitService.add(any()))
                .thenReturn(new Habit("guitar", Collections.emptySet(), this.user, Enums.FrequencyType.Daily, 10 ));

        HabitDto habitDto1 = controller.newHabit(this.customUserDetails, habitDto);
        Assertions.assertNotNull(habitDto1);
        Assertions.assertEquals("guitar", habitDto1.name());;

        // TODO: Assert mocks called
    }

    @Test
    public void testHandleAllRequest() throws Exception {
        List<Habit> habits = Arrays.asList(
                new Habit("guitar", Collections.emptySet(), this.user, Enums.FrequencyType.Daily, 10),
                new Habit("gym", Collections.emptySet(), this.user, Enums.FrequencyType.Daily, 10)
        );

        when(habitService.getAllHabits(any()))
                .thenReturn(habits);

        HabitController controller = new HabitController(this.habitService, this.userService);

        List<HabitDto> habitDtos = controller.all(this.customUserDetails);
        Assertions.assertEquals(2, habitDtos.size());
        Assertions.assertArrayEquals(new String[]{"guitar", "gym"}, habitDtos.stream().map(HabitDto::name).toArray());;
    }
}