package com.edutrackerz.koclukApp.relations;

import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.entities.Teacher;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(
        name = "teacher_student_relation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"teacher_id", "student_id"})
)
public class TeacherStudentRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

}
