
package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.ExamDtoConverter;
import com.edutrackerz.koclukApp.dtos.ExamDTO;
import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {
    
    private final ExamRepository examRepository;
    private final ExamResultService examResultService;
    
    public ExamService(ExamRepository examRepository, ExamResultService examResultService) {
        this.examRepository = examRepository;
        this.examResultService = examResultService;
    }
    
    public List<ExamDTO> getEnterableExamsByStudentId(Long studentId) {
        LocalDateTime now = LocalDateTime.now();
        
        return examRepository.findAll().stream()
                .filter(exam -> exam.getExamDate().isAfter(now))
                .filter(exam -> !examResultService.hasStudentTakenExam(studentId, exam.getId())) 
                .map(ExamDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
