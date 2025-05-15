package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.relations.ExamStudentRelation;
import com.edutrackerz.koclukApp.repository.ExamRepository;
import com.edutrackerz.koclukApp.repository.ExamStudentRelationRepository;
import com.edutrackerz.koclukApp.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamStudentRelationService {

    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final ExamStudentRelationRepository ExamStudentRelationRepository;
    private final TeacherStudentRelationService teacherStudentRelationService;

    public void assignExamToStudent(Long studentId, Long examId, Long teacherId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));        
                boolean isTeacherStudent = teacherStudentRelationService.isStudentBelongToTeacher(teacherId, studentId);
        
        if (!isTeacherStudent) {
            throw new IllegalStateException("Student is not assigned to this teacher");
        }

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));

        boolean alreadyAssigned = ExamStudentRelationRepository
                .findByStudentAndExam(student, exam)
                .isPresent();

        if (alreadyAssigned) {
            throw new IllegalStateException("This exam is already assigned to the student.");
        }

        ExamStudentRelation relation = ExamStudentRelation.builder()
                .student(student)
                .exam(exam)
                .build();

        ExamStudentRelationRepository.save(relation);
    }
      public void assignExamToStudents(List<Long> studentIds, Long examId, Long teacherId) {
        List<String> errors = new ArrayList<>();
        
        for (Long studentId : studentIds) {
            try {
                assignExamToStudent(studentId, examId, teacherId);
            } catch (EntityNotFoundException e) {
                errors.add("Student with ID " + studentId + ": " + e.getMessage());
            } catch (IllegalStateException e) {
                errors.add("Student with ID " + studentId + ": " + e.getMessage());
            } catch (Exception e) {
                errors.add("Error processing student with ID " + studentId + ": " + e.getMessage());
            }
        }
        
        if (!errors.isEmpty()) {
            throw new IllegalStateException("Some students could not be assigned: " + String.join(", ", errors));
        }
    }
}
