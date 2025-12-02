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

        Date start = new Date();
        List<Date> dates = timeParser.parse(text);
        
        if (!dates.isEmpty()) {
            Date parsedDate = dates.get(0);
            if (isDateAndTimeExplicit(text, start, parsedDate)) {
                entities.put("date_obj", parsedDate);
            }
        }

        return entities;
    }

    private boolean isDateAndTimeExplicit(String text, Date start, Date parsed) {
        String lowerText = text.toLowerCase();

        java.util.Calendar startCal = java.util.Calendar.getInstance();
        startCal.setTime(start);

        java.util.Calendar parsedCal = java.util.Calendar.getInstance();
        parsedCal.setTime(parsed);

        if (lowerText.contains("today") || lowerText.contains("tonight")) {
            boolean sameTime = startCal.get(java.util.Calendar.HOUR_OF_DAY) == parsedCal.get(java.util.Calendar.HOUR_OF_DAY) &&
                    Math.abs(startCal.get(java.util.Calendar.MINUTE) - parsedCal.get(java.util.Calendar.MINUTE)) <= 1;
            return !sameTime;
        }

        boolean isSameDay = startCal.get(java.util.Calendar.YEAR) == parsedCal.get(java.util.Calendar.YEAR) &&
                            startCal.get(java.util.Calendar.DAY_OF_YEAR) == parsedCal.get(java.util.Calendar.DAY_OF_YEAR);
        
        return !isSameDay;
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