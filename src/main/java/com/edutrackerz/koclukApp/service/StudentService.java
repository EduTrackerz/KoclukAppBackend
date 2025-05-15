package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.ExamDtoConverter;
import com.edutrackerz.koclukApp.dtos.ExamDTO;
import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.relations.ExamStudentRelation;
import com.edutrackerz.koclukApp.repository.ExamStudentRelationRepository;
import com.edutrackerz.koclukApp.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ExamStudentRelationRepository examStudentRelationRepository;

    public StudentService(StudentRepository studentRepository, 
                        ExamStudentRelationRepository examStudentRelationRepository) {
        this.studentRepository = studentRepository;
        this.examStudentRelationRepository = examStudentRelationRepository;
    }

    public boolean existsById(Long studentId) {
        if (studentId == null) {
            return false;
        }
        return studentRepository.existsById(studentId);
    }

    public List<ExamDTO> getAssignedExams(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
        
        List<ExamStudentRelation> relations = examStudentRelationRepository.findByStudent(student);
        
        return relations.stream()
                .map(relation -> ExamDtoConverter.convertToDto(relation.getExam()))
                .collect(Collectors.toList());
    }
}