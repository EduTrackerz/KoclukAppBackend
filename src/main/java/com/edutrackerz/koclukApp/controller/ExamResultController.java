package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.dtos.ExamResultDto;
import com.edutrackerz.koclukApp.entities.ExamResult;
import com.edutrackerz.koclukApp.repository.ExamResultRepository;
import com.edutrackerz.koclukApp.service.ExamResultService;
import com.edutrackerz.koclukApp.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exam-results")
public class ExamResultController {

    private final ExamResultRepository examResultRepository;
    private final ExamResultService examResultService;
    private final StudentService studentService;

    public ExamResultController(
            ExamResultRepository examResultRepository, 
            ExamResultService examResultService,
            StudentService studentService) {
        this.examResultRepository = examResultRepository;
        this.examResultService = examResultService;
        this.studentService = studentService;
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

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getExamResultsByStudent(@PathVariable Long studentId) {
        try {
            boolean studentExists = studentService.existsById(studentId);
            if (!studentExists) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Öğrenci bulunamadı: " + studentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            List<ExamResultDto> results = examResultService.getExamResultsByStudentId(studentId);
            
            if (results.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Öğrenci için sınav sonucu bulunamadı.");
                response.put("data", Collections.emptyList());
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.ok(results);
                
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Sınav sonuçları getirilirken bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
