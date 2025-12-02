package com.example.demo.controller;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {

    private final OcrService ocrService;

    @PostMapping("/parse")
    public String parseAppointment(@RequestBody AppointmentRequest request) {
        return request.getText();
    }

    @PostMapping(value = "/parse-file", consumes = MULTIPART_FORM_DATA_VALUE)
    public String parseFile(@RequestParam("file")MultipartFile file) {
        try {
            String text = ocrService.extractTextFromStream(file.getInputStream());
            return text;
        } catch (java.io.IOException e) {
            return "Some error occurs";
        }
    }
}
