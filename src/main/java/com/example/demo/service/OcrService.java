package com.example.demo.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class OcrService {
    public String extractTextFromStream(InputStream inputStream) {
        try {
            BufferedImage image = ImageIO.read(inputStream);
            ITesseract instance = new Tesseract();

            // Use absolute path to avoid relative path issues
            Path tessDataPath = Paths.get("tessdata").toAbsolutePath();
            instance.setDatapath(tessDataPath.toString());
            instance.setLanguage("eng");

            return instance.doOCR(image);
        } catch (Exception e) {
            throw new RuntimeException("Error performing OCR: " + e.getMessage(), e);
        }
    }
}
