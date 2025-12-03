package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return OK")
    void testAppoinmentStatusOk1() throws Exception {
        // given
        Map<String, String> requestBody = Map.of(
                "text", "I am having a tooth pain. Can you Book a dentist appoinment for today at 4pm"
        );

        // when + then
        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("Dentist"))
//                .andExpect(jsonPath("$.appointment.date").exists())
                .andExpect(jsonPath("$.appointment.date").value("2025-12-03")) // Add expected date
                .andExpect(jsonPath("$.appointment.time").value("16:00"));
    }

    @Test
    @DisplayName("Should return OK")
    void testAppoinmentStatusOk2() throws Exception {
        // given
        Map<String, String> requestBody = Map.of(
                "text", "Can you Book a doctor appoinment for day after tomorrow at 8pm"
        );

        // when + then
        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("General_practitioner"))
                .andExpect(jsonPath("$.appointment.date").value("2025-12-05"))
                .andExpect(jsonPath("$.appointment.time").value("20:00"));
    }

    @Test
    @DisplayName("Should return OK")
    void testAppoinmentStatusOk3() throws Exception {
        // given
        Map<String, String> requestBody = Map.of(
                "text", "Can you Book a dentist for next monday @ 8pm"
        );

        // when + then
        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("Dentist"))
                .andExpect(jsonPath("$.appointment.date").value("2025-12-08"))
                .andExpect(jsonPath("$.appointment.time").value("20:00"));
    }

    @Test
    @DisplayName("Should return OK")
    void testAppoinmentStatusOk4() throws Exception {
        // given
        Map<String, String> requestBody = Map.of(
                "text", "Can you Book a dentist on 16 of this month @ 8pm"
        );

        // when + then
        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("Dentist"))
                .andExpect(jsonPath("$.appointment.date").value("2025-12-16"))
                .andExpect(jsonPath("$.appointment.time").value("20:00"));
    }

    @Test
    @DisplayName("Should return OK")
    void testAppoinmentStatusOk5() throws Exception {
        // given
        Map<String, String> requestBody = Map.of(
                "text", "I am having a tooth pain. Can you Book a dentist appoinment on 15 of next month at 4pm"
        );

        // when + then
        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("Dentist"))
                .andExpect(jsonPath("$.appointment.date").value("2026-01-15"))
                .andExpect(jsonPath("$.appointment.time").value("16:00"));
    }
}
