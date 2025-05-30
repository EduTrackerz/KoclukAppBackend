package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.GoalDTOConverter;
import com.edutrackerz.koclukApp.dtos.GoalBatchRequestDTO;
import com.edutrackerz.koclukApp.dtos.GoalResponseDTO;
import com.edutrackerz.koclukApp.dtos.SingleGoalDTO;
import com.edutrackerz.koclukApp.entities.Goal;
import com.edutrackerz.koclukApp.entities.Student;
import com.edutrackerz.koclukApp.entities.Teacher;
import com.edutrackerz.koclukApp.entities.Subject;
import com.edutrackerz.koclukApp.repository.GoalRepository;
import com.edutrackerz.koclukApp.repository.StudentRepository;
import com.edutrackerz.koclukApp.repository.TeacherRepository;
import com.edutrackerz.koclukApp.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final GoalDTOConverter goalConverter;

    public GoalService(GoalRepository goalRepository,
                       StudentRepository studentRepository,
                       TeacherRepository teacherRepository,
                       SubjectRepository subjectRepository,
                       GoalDTOConverter goalConverter) {
        this.goalRepository = goalRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.goalConverter = goalConverter;
    }

    public void saveGoals(GoalBatchRequestDTO batchDTO) {
        Teacher teacher = teacherRepository.findById(batchDTO.getTeacherId()).orElseThrow();
        Student student = studentRepository.findById(batchDTO.getStudentId()).orElseThrow();

        List<Goal> goalList = new ArrayList<>();

        for (SingleGoalDTO dto : batchDTO.getGoals()) {
            Subject subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow();
            Goal goal = goalConverter.toEntity(dto, teacher, student, subject);
            goalList.add(goal);
        }

        goalRepository.saveAll(goalList);
    }

    public List<GoalResponseDTO> getGoalsForStudent(Long studentId) {
        List<Goal> goals = goalRepository.findByStudentId(studentId);
        return goals.stream()
                .map(goalConverter::toResponseDTO)
                .toList();
    }

    @Transactional
    public void markGoalAsCompleted(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + goalId));
        goal.setCompleted(true);
        goalRepository.save(goal);
    }

}
