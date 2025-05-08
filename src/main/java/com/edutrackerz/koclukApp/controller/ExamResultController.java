package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.entities.ExamResult;
import com.edutrackerz.koclukApp.repository.ExamResultRepository;
import com.edutrackerz.koclukApp.service.ExamResultService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam-results")
public class ExamResultController {

    private final ExamResultRepository examResultRepository;
    private final ExamResultService examResultService;

    public ExamResultController(ExamResultRepository examResultRepository, ExamResultService examResultService) {
        this.examResultRepository = examResultRepository;
        this.examResultService = examResultService;
    }

    // Sınav sonucu eklemek
    @PostMapping("/add")
    public ResponseEntity<?> addExamResult(@RequestBody ExamResult examResult) {
        try {
            examResultService.validateAnswerCounts(examResult); // validasyon ve boş soru hesaplama burada
            ExamResult saved = examResultRepository.save(examResult);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
