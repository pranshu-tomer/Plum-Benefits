package com.example.demo.service;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityExtractionService {

    private final PrettyTimeParser timeParser = new PrettyTimeParser();

    public Map<String, Object> extractEntities(String text) {
        Map<String, Object> entities = new HashMap<>();

        String department = extractDepartment(text);
        if (department != null) {
            entities.put("department", department);
        }

        List<Date> dates = timeParser.parse(text);
        if (!dates.isEmpty()) {
            entities.put("date_obj", dates.get(0));
        }

        return entities;
    }

    private String extractDepartment(String text) {
        String lowerText = text.toLowerCase();
        if (lowerText.contains("dentist")) return "dentist";
        if (lowerText.contains("doctor")) return "general_practitioner";
        if (lowerText.contains("cardio")) return "cardiology";
        // Add more mappings as needed
        return null;
    }
}