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

//    Update Date
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
                .andExpect(jsonPath("$.appointment.department").value("Dentistry"))
//                .andExpect(jsonPath("$.appointment.date").exists())
                .andExpect(jsonPath("$.appointment.date").value("2025-12-04")) // Add expected date
                .andExpect(jsonPath("$.appointment.time").value("16:00"));
    }

//    Update date
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
                .andExpect(jsonPath("$.appointment.department").value("General_medicine"))
                .andExpect(jsonPath("$.appointment.date").value("2025-12-06"))
                .andExpect(jsonPath("$.appointment.time").value("20:00"));
    }

//    Update date
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
                .andExpect(jsonPath("$.appointment.department").value("Dentistry"))
                .andExpect(jsonPath("$.appointment.date").value("2025-12-08"))
                .andExpect(jsonPath("$.appointment.time").value("20:00"));
    }

//    Update date
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
                .andExpect(jsonPath("$.appointment.department").value("Dentistry"))
                .andExpect(jsonPath("$.appointment.date").value("2025-12-16"))
                .andExpect(jsonPath("$.appointment.time").value("20:00"));
    }

//    Update Date
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
                .andExpect(jsonPath("$.appointment.department").value("Dentistry"))
                .andExpect(jsonPath("$.appointment.date").value("2026-01-15"))
                .andExpect(jsonPath("$.appointment.time").value("16:00"));
    }

    @Test
    @DisplayName("Should return needs_clarification")
    void testAppoinmentStatusNotOk1() throws Exception {
        Map<String, String> requestBody = Map.of(
                "text", "I am having a tooth pain. Can you Book a dentist appoinment for day after tomorrow"
        );

        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("needs_clarification"))
                .andExpect(jsonPath("$.message").value("Ambiguous date/time or department"));
    }

    @Test
    @DisplayName("Should return needs_clarification")
    void testAppoinmentStatusNotOk2() throws Exception {
        Map<String, String> requestBody = Map.of(
                "text", "Can you Book a dentist appoinment at 3pm"
        );

        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("needs_clarification"))
                .andExpect(jsonPath("$.message").value("Ambiguous date/time or department"));
    }

    @Test
    @DisplayName("Should return needs_clarification")
    void testAppoinmentStatusNotOk3() throws Exception {
        Map<String, String> requestBody = Map.of(
                "text", "Can you Book a dentist appoinment for day after at 3pm"
        );

        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("needs_clarification"))
                .andExpect(jsonPath("$.message").value("Ambiguous date/time or department"));
    }

    @Test
    @DisplayName("Should return needs_clarification")
    void testAppoinmentStatusNotOk4() throws Exception {
        Map<String, String> requestBody = Map.of(
                "text", "Can you Book a dentist appoinment at 4pm"
        );

        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("needs_clarification"))
                .andExpect(jsonPath("$.message").value("Ambiguous date/time or department"));
    }

    @Test
    @DisplayName("Should return needs_clarification")
    void testAppoinmentStatusNotOk5() throws Exception {
        Map<String, String> requestBody = Map.of(
                "text", "Can you Book a dentist appoinment for today"
        );

        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("needs_clarification"))
                .andExpect(jsonPath("$.message").value("Ambiguous date/time or department"));
    }

//    Update Date
    @Test
    @DisplayName("Should return OK")
    void testAppoinmentWithLargeInput() throws Exception {
        // given
        Map<String, String> requestBody = Map.of(
                "text", "I wakeup in morning and I am having a tooth pain. " +
                        "Can you Book a dentist appoinment for day after tomorrow @ 4:12 pm. " +
                        "I will go to dentist with my brother. So make sure you book the appoinment. " +
                        "Also inform my brother, I don't want to be late"
        );

        // when + then
        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("Dentistry"))
                .andExpect(jsonPath("$.appointment.date").value("2025-12-06"))
                .andExpect(jsonPath("$.appointment.time").value("16:12"));
    }

    @Test
    @DisplayName("Should map department")
    void testAppoinmentDepartment1() throws Exception {
        Map<String, String> requestBody = Map.of(
                "text", "Please book a doctor appointment for today at 5 pm"
        );

        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("General_medicine"));
    }

    @Test
    @DisplayName("Should map department")
    void testAppoinmentDepartment2() throws Exception {
        Map<String, String> requestBody = Map.of(
                "text", "Please book a dentist appointment for today at 5 pm"
        );

        mockMvc.perform(
                        post("/api/parse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.appointment.department").value("Dentistry"));
    }
}
