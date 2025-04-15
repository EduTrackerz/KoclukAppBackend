package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.converters.StudentDtoConverter;
import com.edutrackerz.koclukApp.dtos.StudentDTO;
import com.edutrackerz.koclukApp.entities.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
}
