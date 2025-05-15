package com.edutrackerz.koclukApp.relations;

import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.entities.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "exam_student_relation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "exam_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExamStudentRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;


}
