package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.converters.TeacherDtoConverter;
import com.edutrackerz.koclukApp.dtos.TeacherDTO;
import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.entities.Teacher;
import com.edutrackerz.koclukApp.repository.TeacherRepository;
import com.edutrackerz.koclukApp.service.ExamStudentRelationService;
import com.edutrackerz.koclukApp.service.TeacherStudentRelationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
public class TeacherController {    private final TeacherRepository teacherRepository;
    private final TeacherStudentRelationService teacherStudentRelationService;
    private final ExamStudentRelationService examStudentRelationService;

    public TeacherController(TeacherRepository teacherRepository, 
                           TeacherStudentRelationService teacherStudentRelationService,
                           ExamStudentRelationService examStudentRelationService) {
        this.teacherRepository = teacherRepository;
        this.teacherStudentRelationService = teacherStudentRelationService;
        this.examStudentRelationService = examStudentRelationService;
    }

    @GetMapping("/getbyid")
    public ResponseEntity<TeacherDTO> getById(@RequestParam Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            return ResponseEntity.ok(TeacherDtoConverter.convertToDto(teacher.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getbyusername")
    public ResponseEntity<TeacherDTO> getByUsername(@RequestParam String username) {
        Optional<Teacher> teacher = teacherRepository.findByUsername(username);
        if (teacher.isPresent()) {
            return ResponseEntity.ok(TeacherDtoConverter.convertToDto(teacher.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<TeacherDTO> register(@RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = TeacherDtoConverter.convertToEntity(teacherDTO);
        Teacher saved = teacherRepository.save(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(TeacherDtoConverter.convertToDto(saved));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<TeacherDTO>> getAll() {
        List<TeacherDTO> teachers = teacherRepository.findAll()
                .stream()
                .map(TeacherDtoConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teachers);
    }

    @PutMapping("/update")
    public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO teacherDTO) {
        if (teacherDTO.getId() == null || !teacherRepository.existsById(teacherDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Teacher teacher = TeacherDtoConverter.convertToEntity(teacherDTO);
        Teacher updated = teacherRepository.save(teacher);
        return ResponseEntity.ok(TeacherDtoConverter.convertToDto(updated));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        if (!teacherRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        teacherRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }    @GetMapping("/{teacherId}/students")
    public ResponseEntity<?> getStudentsOfTeacher(@PathVariable Long teacherId) {
        try {
            List<Student> students = teacherStudentRelationService.getStudentsOfTeacher(teacherId);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Teacher not found with ID: " + teacherId);
        }
    }
      @PostMapping("/{teacherId}/assign-exam")
    public ResponseEntity<?> assignExamToStudents(
            @PathVariable Long teacherId,
            @RequestParam List<Long> studentIdList,
            @RequestParam Long examId) {
        try {
            examStudentRelationService.assignExamToStudents(studentIdList, examId, teacherId);
            return ResponseEntity.ok().body("Sınav öğrencilere başarıyla atandı.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Hata: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Hata: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sınav atama işlemi sırasında bir hata oluştu: " + e.getMessage());
        }
    }
}