package com.example.demo.controller;

import com.example.demo.dto.AppointmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {
    @PostMapping("/parse")
    public String parseAppointment(@RequestBody AppointmentRequest request) {
        return request.getText();
    }
}
