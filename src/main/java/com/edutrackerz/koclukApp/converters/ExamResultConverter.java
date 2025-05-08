package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.ExamResultDto;
import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.entities.ExamResult;

public class ExamResultConverter {

    public static ExamResultDto toDto(ExamResult examResult) {
        if (examResult == null) {
            return null;
        }

        ExamResultDto dto = new ExamResultDto();

        dto.setTurkceCorrect(examResult.getTurkceCorrect());
        dto.setTurkceWrong(examResult.getTurkceWrong());
        dto.setTurkceEmpty(examResult.getTurkceEmpty());

        dto.setMatematikCorrect(examResult.getMatematikCorrect());
        dto.setMatematikWrong(examResult.getMatematikWrong());
        dto.setMatematikEmpty(examResult.getMatematikEmpty());

        dto.setFenCorrect(examResult.getFenCorrect());
        dto.setFenWrong(examResult.getFenWrong());
        dto.setFenEmpty(examResult.getFenEmpty());

        dto.setSosyalCorrect(examResult.getSosyalCorrect());
        dto.setSosyalWrong(examResult.getSosyalWrong());
        dto.setSosyalEmpty(examResult.getSosyalEmpty());

        dto.setDinCorrect(examResult.getDinCorrect());
        dto.setDinWrong(examResult.getDinWrong());
        dto.setDinEmpty(examResult.getDinEmpty());

        dto.setYabanciCorrect(examResult.getYabanciCorrect());
        dto.setYabanciWrong(examResult.getYabanciWrong());
        dto.setYabanciEmpty(examResult.getYabanciEmpty());

        dto.setTotalNet(examResult.getTotalNet());

        Exam exam = examResult.getExam();
        if (exam != null) {
            dto.setExamName(exam.getName());
            dto.setExamDate(exam.getExamDate());
        }

        return dto;
    }
} 