package com.example.demo.controller;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.dto.AppointmentResponse;
import com.example.demo.service.AppoinmentService;
import com.example.demo.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {

    private final OcrService ocrService;
    private final AppoinmentService appoinmentService;

    @PostMapping(value = "/parse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentResponse parseAppointment(@RequestBody AppointmentRequest request) {
        return appoinmentService.processText(request.getText());
    }

    @PostMapping(value = "/parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AppointmentResponse parseFile(@RequestParam("file")MultipartFile file) {
        try {
            String text = ocrService.extractTextFromStream(file.getInputStream());
            return appoinmentService.processText(text);
        } catch (IOException e) {
            throw new RuntimeException("Error while doing OCR"+ e);
        }
    }
}
