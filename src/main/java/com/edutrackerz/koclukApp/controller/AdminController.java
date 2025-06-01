package com.edutrackerz.koclukApp.controller;


import com.edutrackerz.koclukApp.converters.adminDTOConverter;
import com.edutrackerz.koclukApp.dtos.adminDTO;
import com.edutrackerz.koclukApp.entities.Admin;
import com.edutrackerz.koclukApp.repository.AdminRepository;
import com.edutrackerz.koclukApp.relations.TeacherStudentRelation;
import com.edutrackerz.koclukApp.service.TeacherStudentRelationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminRepository adminRepository;
    private final TeacherStudentRelationService teacherStudentRelationService;

    public AdminController(AdminRepository adminRepository, TeacherStudentRelationService teacherStudentRelationService) {
        this.adminRepository = adminRepository;
        this.teacherStudentRelationService = teacherStudentRelationService;
    }

    @GetMapping("/getbyid")
    public ResponseEntity<adminDTO> getById(@RequestParam Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            return ResponseEntity.ok(adminDTOConverter.convertToDto(admin.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<adminDTO> register(@RequestBody adminDTO AdminDTO) {
        Admin admin = adminDTOConverter.convertToEntity(AdminDTO);
        Admin saved = adminRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDTOConverter.convertToDto(saved));
    }

    @GetMapping("/getbyusername")
    public ResponseEntity<adminDTO> getByUsername(@RequestParam String username ) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return ResponseEntity.ok(adminDTOConverter.convertToDto(admin.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/assign-student-to-teacher")
    public ResponseEntity<?> assignStudentToTeacher(@RequestParam Long teacherId, @RequestParam Long studentId) {
        try {
            TeacherStudentRelation relation = teacherStudentRelationService.createTeacherStudentRelation(teacherId, studentId);
            return ResponseEntity.ok("Student with ID " + studentId +
                    " successfully assigned to teacher with ID " + teacherId);
        } catch (IllegalStateException e) {
            // Özel hata: Öğrenci zaten atanmışsa
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage())); // frontend beklediği gibi JSON formatı
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Öğrenci atanamadı: " + e.getMessage()));
        }
    }

}
