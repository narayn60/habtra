package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserService.class, UserRepository.class})
class HabitEntryControllerTest {

    private MockMvc mvc;

    @Mock
    private HabitEntryService service;

    @Autowired
    private UserService userService;

    @InjectMocks
    private HabitEntryController controller;

    @Autowired
    private JacksonTester<ArrayList<HabitEntry>> jsonHabitEntryArray;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void testGetHabitEntries() throws Exception {
        User user = userService.create(new User("user", "password"));

        Habit habit = new Habit("guitar", Collections.emptySet(), user);

        // TODO: Figure out something better
        Timestamp startTime = new Timestamp(System.currentTimeMillis());

        ArrayList<HabitEntry> habitEntries = new ArrayList<>();
        habitEntries.add(new HabitEntry(habit, startTime, null));

        given(service.getAll()).willReturn(habitEntries);

        MockHttpServletResponse response = mvc.perform(
                get("/habitEntries").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonHabitEntryArray.write(habitEntries).getJson()
        );
    }

    @Test
    void createHabitEntry() {
    }

    @Test
    void putHabitEntry() {
    }
}