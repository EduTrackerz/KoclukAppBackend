package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.converters.StudentDtoConverter;
import com.edutrackerz.koclukApp.dtos.StudentDTO;
import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    }
}
