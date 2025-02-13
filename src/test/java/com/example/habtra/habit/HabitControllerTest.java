package com.example.habtra.habit;

import com.example.habtra.user.CustomUserDetails;
import com.example.habtra.user.User;
import com.example.habtra.user.UserRepository;
import com.example.habtra.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureJsonTesters
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserService.class, UserRepository.class})
class HabitControllerTest {

    private MockMvc mvc;

    @Mock
    private HabitService habitService;

    @Autowired
    private UserService userService;

    @InjectMocks
    private HabitController habitController;

    @Autowired
    private JacksonTester<Habit> jsonHabit;

    @Autowired
    private JacksonTester<ArrayList<Habit>> jsonHabitArray;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(habitController)
                .build();
    }

    @Test
    public void testHandleNewHabitRequest() throws Exception {
        User user = userService.create(new User("user", "password", "user@email.com"));

        Habit habit = new Habit("guitar", Collections.emptySet(), user);

        MockHttpServletResponse response =
                mvc.perform(
                        post("/habits/").contentType(MediaType.APPLICATION_JSON)
                                .content(jsonHabit.write(habit).getJson()
                                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void testHandleAllRequest() throws Exception {
        User user = userService.create(new User("user", "password", "user@email.com"));

        CustomUserDetails userDetails = new CustomUserDetails("user", "password", user.getId(), Collections.emptyList());
        ArrayList<Habit> habits = new ArrayList<>();
        habits.add(new Habit("Guitar", Collections.emptySet(), user));

        // given
        given(habitService.getAllHabits(user.getId())).willReturn(habits);

        MockHttpServletResponse response = mvc.perform(
                get("/habits").with(user(userDetails)).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonHabitArray.write(habits).getJson()
        );
    }
}