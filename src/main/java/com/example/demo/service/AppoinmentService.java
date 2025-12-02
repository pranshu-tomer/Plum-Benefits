package com.example.demo.service;

import com.example.demo.dto.AppointmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppoinmentService {

    private final EntityExtractionService entityExtractionService;
    private final NormalizationService normalizationService;

    public AppointmentResponse processText(String textToProcess) {
        // Step 2: Entity Extraction
        Map<String, Object> entities = entityExtractionService.extractEntities(textToProcess);

        if (!entities.containsKey("date_obj") || !entities.containsKey("department")) {
            return AppointmentResponse.builder()
                    .status("needs_clarification")
                    .message("Ambiguous date/time or department")
                    .build();
        }

        Map<String, Object> normalized = normalizationService.normalize(entities);

        return AppointmentResponse.builder()
                .status("ok")
                .appointment(normalized)
                .build();
    }

}
