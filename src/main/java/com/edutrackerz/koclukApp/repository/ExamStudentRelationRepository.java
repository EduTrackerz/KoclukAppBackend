package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.relations.ExamStudentRelation;
import com.edutrackerz.koclukApp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ExamStudentRelationRepository extends JpaRepository<ExamStudentRelation, Long> {
    List<ExamStudentRelation> findByStudent(Student student);
    Optional<ExamStudentRelation> findByStudentAndExam(Student student, Exam exam);

}
