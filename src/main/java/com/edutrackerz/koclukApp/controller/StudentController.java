package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.converters.StudentDtoConverter;
import com.edutrackerz.koclukApp.dtos.ExamDTO;
import com.edutrackerz.koclukApp.dtos.StudentDTO;
import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.entities.Teacher;
import com.edutrackerz.koclukApp.repository.StudentRepository;
import com.edutrackerz.koclukApp.service.StudentService;
import com.edutrackerz.koclukApp.service.TeacherStudentRelationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {    private final StudentRepository studentRepository;
    private final TeacherStudentRelationService teacherStudentRelationService;
    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository, 
                            TeacherStudentRelationService teacherStudentRelationService,
                            StudentService studentService) {
        this.studentRepository = studentRepository;
        this.teacherStudentRelationService = teacherStudentRelationService;
        this.studentService = studentService;
    }

    @GetMapping("/getbyid")
    public ResponseEntity<StudentDTO> getById(@RequestParam Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(StudentDtoConverter.convertToDto(student.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getbyusername")
    public ResponseEntity<StudentDTO> getByUsername(@RequestParam String username ) {
        Optional<Student> student = studentRepository.findByUsername(username);
        if (student.isPresent()) {
            return ResponseEntity.ok(StudentDtoConverter.convertToDto(student.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<StudentDTO> register(@RequestBody StudentDTO studentDTO) {
        Student student = StudentDtoConverter.convertToEntity(studentDTO);
        Student saved = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(StudentDtoConverter.convertToDto(saved));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> students = studentRepository.findAll()
                .stream()
                .map(StudentDtoConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    @PutMapping("/update")
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO) {
        if (studentDTO.getId() == null || !studentRepository.existsById(studentDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Student student = StudentDtoConverter.convertToEntity(studentDTO);
        Student updated = studentRepository.save(student);
        return ResponseEntity.ok(StudentDtoConverter.convertToDto(updated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }    @GetMapping("/{studentId}/teachers")
    public ResponseEntity<?> getTeachersOfStudent(@PathVariable Long studentId) {
        try {
            List<Teacher> teachers = teacherStudentRelationService.getTeachersOfStudent(studentId);
            return ResponseEntity.ok(teachers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with ID: " + studentId);
        }
    }
    
    @GetMapping("/{studentId}/assigned-exams")
    public ResponseEntity<?> getAssignedExams(@PathVariable Long studentId) {
        try {
            List<ExamDTO> assignedExams = studentService.getAssignedExams(studentId);
              if (assignedExams.isEmpty()) {
                return ResponseEntity.ok()
                        .body(Collections.singletonMap("message", "Bu öğrenciye atanmış sınav bulunmamaktadır."));
            }
            
            return ResponseEntity.ok(assignedExams);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Öğrenciye atanmış sınavlar getirilirken bir hata oluştu: " + e.getMessage()));
        }
    }
}
