package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.GoalResponseDTO;
import com.edutrackerz.koclukApp.dtos.SingleGoalDTO;
import com.edutrackerz.koclukApp.entities.Goal;
import com.edutrackerz.koclukApp.entities.Subject;
import com.edutrackerz.koclukApp.entities.Teacher;
import com.edutrackerz.koclukApp.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class GoalDTOConverter {

    public GoalResponseDTO toResponseDTO(Goal goal) {
        return GoalResponseDTO.builder()
                .id(goal.getId())
                .subjectName(goal.getSubject().getName())
                .questionCount(goal.getQuestionCount())
                .deadline(goal.getDeadline())
                .teacherName(goal.getTeacher().getName()) // ya da getFullName()
                .build();
    }

    public Goal toEntity(SingleGoalDTO dto, Teacher teacher, Student student, Subject subject) {
        return Goal.builder()
                .teacher(teacher)
                .student(student)
                .subject(subject)
                .questionCount(dto.getQuestionCount())
                .deadline(dto.getDeadline())
                .build();
    }
}
