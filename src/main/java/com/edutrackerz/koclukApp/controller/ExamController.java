package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.repository.ExamRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamRepository examRepository;

    public ExamController(ExamRepository examRepository) {
        this.examRepository = examRepository;
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
}
