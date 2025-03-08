package com.example.habtra.habitEntry;

import com.example.habtra.habit.Habit;
import com.example.habtra.habit.HabitService;
import com.example.habtra.habitEntry.dtos.HabitEntryDto;
import com.example.habtra.types.Enums;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HabitEntryController.class)
class HabitEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitEntryService service;

    @MockBean
    private HabitService habitService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createHabitEntry() {
    }

    @Test
    @WithMockUser
    void getHabitEntries() throws Exception {
        Habit habit = new Habit("test_habit", null, null, Enums.FrequencyType.Daily, 1);
        HabitEntry habitEntry = new HabitEntryBuilder().createHabitEntry();
        UUID id = UUID.randomUUID();
        habitEntry.setId(id);
        habitEntry.setHabit(habit);


        when(service.getAll()).thenReturn(Collections.singletonList(habitEntry));
        MvcResult result = this.mockMvc.perform(get("/habitEntries"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<HabitEntryDto> entries = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(entries);
        assertEquals(1, entries.size());

        assertEquals(id, entries.getFirst().id());
    }

    @Test
    void getHabitEntry() {
    }
}