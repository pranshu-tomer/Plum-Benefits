package com.example.demo.dto;

import lombok.Data;
import lombok.Builder;
import java.util.Map;

@Data
@Builder
public class AppointmentResponse {
    private Map<String, Object> appointment;
    private String status;
    private String message;
}
