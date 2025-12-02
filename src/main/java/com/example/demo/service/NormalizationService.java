package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class NormalizationService {

    private static final String TARGET_TIMEZONE = "Asia/Kolkata";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public Map<String, Object> normalize(Map<String, Object> entities) {
        Map<String, Object> normalized = new HashMap<>();

        if (entities.containsKey("date_obj")) {
            Date dateObj = (Date) entities.get("date_obj");
            ZonedDateTime zdt = dateObj.toInstant().atZone(ZoneId.of(TARGET_TIMEZONE));

            normalized.put("date", zdt.format(DATE_FORMATTER));
            normalized.put("time", zdt.format(TIME_FORMATTER));
            normalized.put("tz", TARGET_TIMEZONE);
        }

        if (entities.containsKey("department")) {
            // Capitalize for final output
            String dept = (String) entities.get("department");
            normalized.put("department", dept.substring(0, 1).toUpperCase() + dept.substring(1));
        }

        return normalized;
    }
}
