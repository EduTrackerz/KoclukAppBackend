package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.ExamResultConverter;
import com.edutrackerz.koclukApp.dtos.ExamResultDto;
import com.edutrackerz.koclukApp.entities.Exam;
import com.edutrackerz.koclukApp.entities.ExamResult;
import com.edutrackerz.koclukApp.entities.ExamResultID;
import com.edutrackerz.koclukApp.repository.ExamRepository;
import com.edutrackerz.koclukApp.repository.ExamResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamResultService {
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;

    public ExamResultService(ExamRepository examRepository, ExamResultRepository examResultRepository) {
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
    }

    public void validateAnswerCounts(ExamResult examResult) {

        Exam exam = examRepository.findById(examResult.getExam().getId())
                .orElseThrow(() -> new IllegalArgumentException("Exam not found"));
        examResult.setExam(exam);

        // 1 - Sınav tarihi kontolü
        validateExamDate(exam);

        // 2 - Ders bazlı doğru-yanlis kontrolü (toplam soru sayisi gecilmemeli)
        validateAnswers(exam.getTurkceCount(), examResult.getTurkceCorrect(), examResult.getTurkceWrong(), "Turkce");
        validateAnswers(exam.getMatematikCount(), examResult.getMatematikCorrect(), examResult.getMatematikWrong(), "Matematik");
        validateAnswers(exam.getFenCount(), examResult.getFenCorrect(), examResult.getFenWrong(), "Fen Bilgisi");
        validateAnswers(exam.getSosyalCount(), examResult.getSosyalCorrect(), examResult.getSosyalWrong(), "Sosyal Bilgiler");
        validateAnswers(exam.getDinCount(), examResult.getDinCorrect(), examResult.getDinWrong(), "Din Kültürü");
        validateAnswers(exam.getYabanciCount(), examResult.getYabanciCorrect(), examResult.getYabanciWrong(), "Yabanci Dil");


        // 3 - Eğer validasyon geçtiyse boşları otomatik hesapla
        examResult.setTurkceEmpty(calculateEmpty(exam.getTurkceCount(), examResult.getTurkceCorrect(), examResult.getTurkceWrong()));
        examResult.setMatematikEmpty(calculateEmpty(exam.getMatematikCount(), examResult.getMatematikCorrect(), examResult.getMatematikWrong()));
        examResult.setFenEmpty(calculateEmpty(exam.getFenCount(), examResult.getFenCorrect(), examResult.getFenWrong()));
        examResult.setSosyalEmpty(calculateEmpty(exam.getSosyalCount(), examResult.getSosyalCorrect(), examResult.getSosyalWrong()));
        examResult.setDinEmpty(calculateEmpty(exam.getDinCount(), examResult.getDinCorrect(), examResult.getDinWrong()));
        examResult.setYabanciEmpty(calculateEmpty(exam.getYabanciCount(), examResult.getYabanciCorrect(), examResult.getYabanciWrong()));

        // 4 - Toplam net sayısını hesapla
        examResult.setTotalNet(calculateTotalNet(examResult));
    }

    private void validateAnswers(int totalQuestions, int correct, int wrong, String subject) {
        if (correct < 0 || wrong < 0) {
            throw new IllegalArgumentException("Correct or Wrong answer fields cannot be negative.");
        }
        if (correct + wrong > totalQuestions) {
            throw new IllegalArgumentException(subject + " total correct and wrong answers exceed the total number of questions.");
        }
    }

    private int calculateEmpty(int totalQuestions, int correct, int wrong) {
        return Math.max(0, totalQuestions - (correct + wrong));
    }

    private double calculateTotalNet(ExamResult examResult) {
        int totalCorrect = examResult.getTurkceCorrect() + examResult.getMatematikCorrect() + examResult.getFenCorrect()
                + examResult.getSosyalCorrect() + examResult.getDinCorrect() + examResult.getYabanciCorrect();

        int totalWrong = examResult.getTurkceWrong() + examResult.getMatematikWrong() + examResult.getFenWrong()
                + examResult.getSosyalWrong() + examResult.getDinWrong() + examResult.getYabanciWrong();


        return totalCorrect - (totalWrong / 4.0);
    }

    private void validateExamDate(Exam exam) {
        LocalDateTime now = LocalDateTime.now();
        if (exam.getExamDate().isAfter(now)) {
            throw new IllegalArgumentException("Exam date has not arrived yet. You cannot submit results.");
        }
    }

    public List<ExamResultDto> getExamResultsByStudentId(Long studentId) {
        List<ExamResult> examResults = examResultRepository.findByStudentId(studentId);
        return examResults.stream()
                .map(ExamResultConverter::toDto)
                .collect(Collectors.toList());
    }
    
    // Yeni eklenen metot - öğrencinin belirli bir sınava girip girmediğini kontrol eder
    public boolean hasStudentTakenExam(Long studentId, Long examId) {
        ExamResultID id = new ExamResultID(studentId, examId);
        return examResultRepository.existsById(id);
    }
}
