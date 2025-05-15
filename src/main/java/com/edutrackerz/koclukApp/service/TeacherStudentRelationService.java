package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.entities.Teacher;
import com.edutrackerz.koclukApp.relations.TeacherStudentRelation;
import com.edutrackerz.koclukApp.repository.TeacherStudentRelationRepository;
import com.edutrackerz.koclukApp.repository.TeacherRepository;
import com.edutrackerz.koclukApp.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherStudentRelationService {

    private final TeacherStudentRelationRepository teacherStudentRelationRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TeacherStudentRelationService(
            TeacherStudentRelationRepository teacherStudentRelationRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository) {
        this.teacherStudentRelationRepository = teacherStudentRelationRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudentsOfTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
                
        return teacherStudentRelationRepository.findByTeacher(teacher)
                .stream()
                .map(TeacherStudentRelation::getStudent)
                .toList();
    }


    public List<Teacher> getTeachersOfStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return teacherStudentRelationRepository.findByStudent(student)
                .stream()
                .map(TeacherStudentRelation::getTeacher)
                .toList();
    }

    public TeacherStudentRelation createTeacherStudentRelation(Long teacherId, Long studentId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
        
        Optional<TeacherStudentRelation> existingRelation = 
            teacherStudentRelationRepository.findByTeacherAndStudent(teacher, student);
        
        if (existingRelation.isPresent()) {
            throw new IllegalStateException("Teacher-Student relation already exists");
        }
        TeacherStudentRelation relation = new TeacherStudentRelation();
        relation.setTeacher(teacher);
        relation.setStudent(student);
        
        return teacherStudentRelationRepository.save(relation);
    }
}
