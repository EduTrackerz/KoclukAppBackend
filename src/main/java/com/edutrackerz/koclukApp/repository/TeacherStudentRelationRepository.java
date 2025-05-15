package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.entities.Teacher;
import com.edutrackerz.koclukApp.relations.TeacherStudentRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherStudentRelationRepository extends JpaRepository<TeacherStudentRelation, Long> {

    List<TeacherStudentRelation> findByTeacher(Teacher teacher);

    List<TeacherStudentRelation> findByStudent(Student student);

    Optional<TeacherStudentRelation> findByTeacherAndStudent(Teacher teacher, Student student);
}
