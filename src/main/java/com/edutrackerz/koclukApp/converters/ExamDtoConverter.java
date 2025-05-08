package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.ExamDTO;
import com.edutrackerz.koclukApp.entities.Exam;

public class ExamDtoConverter {

    public static ExamDTO convertToDto(Exam exam) {
        if (exam == null) {
            return null;
        }
        
        return new ExamDTO(
            exam.getId(),
            exam.getName(),
            exam.getTurkceCount(),
            exam.getMatematikCount(),
            exam.getFenCount(),
            exam.getSosyalCount(),
            exam.getDinCount(),
            exam.getYabanciCount(),
            exam.getExamDate()
        );
    }

    public static Exam convertToEntity(ExamDTO examDTO) {
        if (examDTO == null) {
            return null;
        }
        
        Exam exam = new Exam();
        exam.setId(examDTO.getId());
        exam.setName(examDTO.getName());
        exam.setTurkceCount(examDTO.getTurkceCount());
        exam.setMatematikCount(examDTO.getMatematikCount());
        exam.setFenCount(examDTO.getFenCount());
        exam.setSosyalCount(examDTO.getSosyalCount());
        exam.setDinCount(examDTO.getDinCount());
        exam.setYabanciCount(examDTO.getYabanciCount());
        exam.setExamDate(examDTO.getExamDate());
        
        return exam;
    }
} 