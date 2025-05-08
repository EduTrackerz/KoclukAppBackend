package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.dtos.ExamDTO;
import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.repository.ExamRepository;
import com.edutrackerz.koclukApp.service.ExamService;
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
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamRepository examRepository;
    private final StudentService studentService;
    private final ExamService examService;

    public ExamController(ExamRepository examRepository, StudentService studentService, ExamService examService) {
        this.examRepository = examRepository;
        this.studentService = studentService;
        this.examService = examService;
    }

    // Sınav eklemek (Sadece ADMIN rolü)
    @PostMapping("/add")
    public ResponseEntity<String> addExam(@RequestBody @Valid Exam exam, @RequestHeader("Role") String role) {
        if (!role.equalsIgnoreCase("ADMIN")) {
            return ResponseEntity.status(403).body("Access denied");
        }
        examRepository.save(exam);
        return ResponseEntity.ok("Exam added successfully!");
    }

    // Sınavları listelemek (ADMIN ve STUDENT görebilir)
    @GetMapping("/all")
    public ResponseEntity<?> getAllExams(@RequestHeader("Role") String role) {
        if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("STUDENT"))) {
            return ResponseEntity.status(403).body("Access denied");
        }
        List<Exam> exams = examRepository.findAllByOrderByExamDateAsc();
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/enterable/{studentId}")
    public ResponseEntity<?> getEnterableExams(@PathVariable Long studentId) {
        try {
            if (!studentService.existsById(studentId)) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Öğrenci bulunamadı: " + studentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            List<ExamDTO> enterableExams = examService.getEnterableExamsByStudentId(studentId);
            
            if (enterableExams.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Öğrenci için girilebilecek sınav bulunamadı.");
                response.put("data", Collections.emptyList());
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.ok(enterableExams);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Girilebilecek sınav listesi alınırken bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
