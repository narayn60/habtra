package com.example.habtra.habit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureJsonTesters
@ExtendWith(MockitoExtension.class)
class HabitControllerTest {

    private MockMvc mvc;

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitController habitController;

    @Autowired
    private JacksonTester<ArrayList<Habit>> jsonSuperHero;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(habitController)
                .build();
    }

    @Test
    void all() {
    }

    @Test
    void newHabit() {
    }

    @Test
    public void testHandleAllRequest() throws Exception {
        ArrayList<Habit> habits = new ArrayList<>();
        habits.add(new Habit("Guitar", Collections.emptySet()));
        // given
        given(habitRepository.findAll()).willReturn(habits);

        MockHttpServletResponse response = mvc.perform(
                get("/habits").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSuperHero.write(habits).getJson()
        );
    }
}