package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.dtos.GoalBatchRequestDTO;
import com.edutrackerz.koclukApp.dtos.GoalResponseDTO;
import com.edutrackerz.koclukApp.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    // Öğretmen hedef tanımlar, çoklu olabilir
    @PostMapping
    public ResponseEntity<Map<String, String>> createGoals(@RequestBody GoalBatchRequestDTO dto) {
        try {
            goalService.saveGoals(dto);
            return ResponseEntity.ok(Collections.singletonMap("message", "Hedefler başarıyla kaydedildi."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Hedef kaydı başarısız: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Sunucu hatası: " + e.getMessage()));
        }
    }

    // Öğrenci hedeflerini görür
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GoalResponseDTO>> getGoalsForStudent(@PathVariable Long studentId) {
        List<GoalResponseDTO> response = goalService.getGoalsForStudent(studentId);
        return ResponseEntity.ok(response);
    }
}
