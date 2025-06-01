package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.ExamResultConverter;
import com.edutrackerz.koclukApp.dtos.ExamBriefResultDto;
import com.edutrackerz.koclukApp.dtos.ExamDetailedResultDto;
import com.edutrackerz.koclukApp.dtos.ExamResultRequestDTO;
import com.edutrackerz.koclukApp.entities.*;
import com.edutrackerz.koclukApp.repository.ExamRepository;
import com.edutrackerz.koclukApp.repository.ExamResultRepository;
import com.edutrackerz.koclukApp.repository.StudentRepository;
import com.edutrackerz.koclukApp.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamResultService {

    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;
    private final ExamResultConverter examResultConverter;

    public ExamResultService(
            ExamRepository examRepository,
            ExamResultRepository examResultRepository,
            StudentRepository studentRepository,
            TopicRepository topicRepository,
            ExamResultConverter examResultConverter
    ) {
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
        this.studentRepository = studentRepository;
        this.topicRepository = topicRepository;
        this.examResultConverter = examResultConverter;
    }

    @Transactional
    public ExamResult saveExamResult(ExamResultRequestDTO request, Long studentId, Long examId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with id: " + examId));

        validateExamDate(exam);

        // Aynı sınavı daha önce kaydetmiş mi?
        if (hasStudentTakenExam(student.getId(), exam.getId())) {
            throw new IllegalArgumentException("Student has already submitted results for this exam.");
        }

        // Yeni ExamResult oluştur
        ExamResult examResult = new ExamResult();
        ExamResultID examResultID = new ExamResultID(student.getId(), exam.getId());
        examResult.setId(examResultID);
        examResult.setStudent(student);
        examResult.setExam(exam);

        // 1) “generalResults” kısmını entity’ye ata
        Map<String, ExamResultRequestDTO.GeneralResult> general = request.getGeneralResults();
        // JSON’da anahtarlar tam olarak "turkce", "matematik", "fen", "sosyal", "din", "yabanci" olduğu için:
        examResult.setTurkceCorrect(general.getOrDefault("turkce", new ExamResultRequestDTO.GeneralResult()).getCorrect());
        examResult.setTurkceWrong(  general.getOrDefault("turkce", new ExamResultRequestDTO.GeneralResult()).getWrong());
        examResult.setTurkceEmpty(  general.getOrDefault("turkce", new ExamResultRequestDTO.GeneralResult()).getEmpty());

        examResult.setMatematikCorrect(general.getOrDefault("matematik", new ExamResultRequestDTO.GeneralResult()).getCorrect());
        examResult.setMatematikWrong(  general.getOrDefault("matematik", new ExamResultRequestDTO.GeneralResult()).getWrong());
        examResult.setMatematikEmpty(  general.getOrDefault("matematik", new ExamResultRequestDTO.GeneralResult()).getEmpty());

        examResult.setFenCorrect(general.getOrDefault("fen", new ExamResultRequestDTO.GeneralResult()).getCorrect());
        examResult.setFenWrong(  general.getOrDefault("fen", new ExamResultRequestDTO.GeneralResult()).getWrong());
        examResult.setFenEmpty(  general.getOrDefault("fen", new ExamResultRequestDTO.GeneralResult()).getEmpty());

        examResult.setSosyalCorrect(general.getOrDefault("sosyal", new ExamResultRequestDTO.GeneralResult()).getCorrect());
        examResult.setSosyalWrong(  general.getOrDefault("sosyal", new ExamResultRequestDTO.GeneralResult()).getWrong());
        examResult.setSosyalEmpty(  general.getOrDefault("sosyal", new ExamResultRequestDTO.GeneralResult()).getEmpty());

        examResult.setDinCorrect(general.getOrDefault("din", new ExamResultRequestDTO.GeneralResult()).getCorrect());
        examResult.setDinWrong(  general.getOrDefault("din", new ExamResultRequestDTO.GeneralResult()).getWrong());
        examResult.setDinEmpty(  general.getOrDefault("din", new ExamResultRequestDTO.GeneralResult()).getEmpty());

        examResult.setYabanciCorrect(general.getOrDefault("yabanci", new ExamResultRequestDTO.GeneralResult()).getCorrect());
        examResult.setYabanciWrong(  general.getOrDefault("yabanci", new ExamResultRequestDTO.GeneralResult()).getWrong());
        examResult.setYabanciEmpty(  general.getOrDefault("yabanci", new ExamResultRequestDTO.GeneralResult()).getEmpty());

        // Önce kaydet ki GeneratedId (embed ID) atansın
        examResultRepository.save(examResult);

        // 2) “detailedScores” kısmından TopicResult’ları oluştur
        //    request.getDetailedScores(): Map< String subjectKey, Map< Long topicId, TopicWrongOnlyWrapper >>
        Set<TopicResult> topicResults = request.getDetailedScores().entrySet().stream()
                .flatMap(subjectEntry -> subjectEntry.getValue().entrySet().stream())
                .map(topicEntry -> {
                    Long topicId   = topicEntry.getKey();
                    int wrongCount = topicEntry.getValue().getIncorrectAnswers();

                    Topic topic = topicRepository.findById(topicId)
                            .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + topicId));

                    TopicResult tr = new TopicResult();
                    tr.setExamResult(examResult);
                    tr.setTopic(topic);
                    tr.setWrongCount(wrongCount);
                    return tr;
                })
                .collect(Collectors.toSet());

        examResult.setTopicResults(topicResults);

        // Tekrar kaydet (ilişkileri commit etmek için)
        return examResultRepository.save(examResult);
    }

    private void validateExamDate(Exam exam) {
        if (exam.getExamDate().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Exam date has not arrived yet. You cannot submit results.");
        }
    }

    public List<ExamBriefResultDto> getExamResultsBriefByStudentId(Long studentId) {
        return examResultRepository.findByStudentId(studentId).stream()
                .map(examResultConverter::convertToBriefDto)
                .collect(Collectors.toList());
    }

    public ExamDetailedResultDto getDetailedResultForStudent(Long studentId, Long examId) {
        ExamResultID id = new ExamResultID(studentId, examId);
        return examResultRepository.findById(id)
                .map(examResultConverter::convertToDetailedDto)
                .orElse(null);
    }

    public boolean hasStudentTakenExam(Long studentId, Long examId) {
        return examResultRepository.existsById(new ExamResultID(studentId, examId));
    }
}
