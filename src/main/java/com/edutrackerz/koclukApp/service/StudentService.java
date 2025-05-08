package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean existsById(Long studentId) {
        if (studentId == null) {
            return false;
        }
        return studentRepository.existsById(studentId);
    }
} 