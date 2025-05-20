package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.ExamResultConverter;
import com.edutrackerz.koclukApp.dtos.ExamBriefResultDto;
import com.edutrackerz.koclukApp.dtos.ExamDetailedResultDto;
import com.edutrackerz.koclukApp.dtos.TopicResultDto;
import com.edutrackerz.koclukApp.dtos.TopicDTO; // Added import
import com.edutrackerz.koclukApp.entities.*;
import com.edutrackerz.koclukApp.repository.ExamRepository;
import com.edutrackerz.koclukApp.repository.ExamResultRepository;
import com.edutrackerz.koclukApp.repository.StudentRepository; // Added
import com.edutrackerz.koclukApp.repository.TopicRepository; // Added
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Added

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamResultService {
    @Autowired
    private Environment env;

    @Value("${subject.turkce}")
    private static String turkce;

    @Value("${subject.matematik}")
    private static String matematik;

    @Value("${subject.fen}")
    private static String fen;

    @Value("${subject.sosyal}")
    private static String sosyal;

    @Value("${subject.din}")
    private static String din;

    @Value("${subject.dil}")
    private static String dil;
    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;
    private final TopicRepository topicRepository;
    private final TopicService topicService;
    private final ExamResultConverter examResultConverter;

    @PostConstruct
    public void init() {
        turkce = env.getProperty("subject.turkce");
        matematik = env.getProperty("subject.matematik");
        fen = env.getProperty("subject.fen");
        sosyal = env.getProperty("subject.sosyal");
        din = env.getProperty("subject.din");
        dil = env.getProperty("subject.dil");
    }
    public ExamResultService(ExamRepository examRepository, ExamResultRepository examResultRepository, StudentRepository studentRepository, TopicRepository topicRepository, TopicService topicService, ExamResultConverter examResultConverter) {
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
        this.studentRepository = studentRepository;
        this.topicRepository = topicRepository;
        this.topicService = topicService;
        this.examResultConverter = examResultConverter;
    }

    @Transactional
    public ExamResult saveExamResult(ExamDetailedResultDto examDetailedResultDto, Long studentId, Long examId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new IllegalArgumentException("Exam not found with id: " + examId));

        validateExamDate(exam);

        if (hasStudentTakenExam(student.getId(), exam.getId())) {
            throw new IllegalArgumentException("Student has already submitted results for this exam.");
        }

        ExamResult examResult = new ExamResult();
        ExamResultID examResultID = new ExamResultID(student.getId(), exam.getId());
        examResult.setId(examResultID);
        examResult.setStudent(student);
        examResult.setExam(exam);

        // Save topic results
        // Extract topic IDs from detailedScores for validation
        List<TopicResultDto> topicResultDtos = new ArrayList<>();
        examDetailedResultDto.getDetailedScores().forEach((subjectId, topicScores) -> {
            topicScores.forEach((topicIdStr, scores) -> {
                TopicResultDto dto = new TopicResultDto();
                dto.setTopicId(Long.parseLong(topicIdStr));
                topicResultDtos.add(dto);
            });
        });

        // Validate that all required topics are submitted
        validateSubmittedTopics(topicResultDtos);

        // Create the TopicResult entities
        Set<TopicResult> topicResults = examDetailedResultDto.getDetailedScores().entrySet().stream().flatMap(subjectEntry -> subjectEntry.getValue().entrySet().stream()).map(topicEntry -> {
            TopicResult topicResult = new TopicResult();
            topicResult.setExamResult(examResult);
            topicResult.setTopic(topicRepository.findById(Long.parseLong(topicEntry.getKey())).orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + topicEntry.getKey())));
            topicResult.setCorrectCount(topicEntry.getValue().getCorrectAnswers());
            topicResult.setWrongCount(topicEntry.getValue().getIncorrectAnswers());
            topicResult.setEmptyCount(topicEntry.getValue().getBlankAnswers());
            return topicResult;
        }).collect(Collectors.toSet());

        examResult.setTopicResults(topicResults);

        // Validate that the total answer count matches the exam question count for each subject
        validateSubjectAnswerCounts(exam, topicResults);

        return examResultRepository.save(examResult);
    }

    private void validateSubjectAnswerCounts(Exam exam, Set<TopicResult> topicResults) {
        Map<String, Integer> subjectAnswerCounts = new HashMap<>();

        // Calculate answer counts per subject
        for (TopicResult tr : topicResults) {
            if (tr.getTopic() != null && tr.getTopic().getSubject() != null && tr.getTopic().getSubject().getName() != null) {
                String subjectName = tr.getTopic().getSubject().getName();
                int totalAnswersForTopic = tr.getCorrectCount() + tr.getWrongCount() + tr.getEmptyCount();
                subjectAnswerCounts.put(subjectName, subjectAnswerCounts.getOrDefault(subjectName, 0) + totalAnswersForTopic);
            }
        }

        // Validate against exam question counts
        StringBuilder errorMessage = new StringBuilder();

        if (subjectAnswerCounts.getOrDefault(turkce, 0) != exam.getTurkceCount()) {
            errorMessage.append("Türkçe answer count (").append(subjectAnswerCounts.getOrDefault(turkce, 0)).append(") doesn't match exam question count (").append(exam.getTurkceCount()).append("). ");
        }

        if (subjectAnswerCounts.getOrDefault(matematik, 0) != exam.getMatematikCount()) {
            errorMessage.append("Matematik answer count (").append(subjectAnswerCounts.getOrDefault(matematik, 0)).append(") doesn't match exam question count (").append(exam.getMatematikCount()).append("). ");
        }

        if (subjectAnswerCounts.getOrDefault(fen, 0) != exam.getFenCount()) {
            errorMessage.append("Fen Bilimleri answer count (").append(subjectAnswerCounts.getOrDefault(fen, 0)).append(") doesn't match exam question count (").append(exam.getFenCount()).append("). ");
        }

        if (subjectAnswerCounts.getOrDefault(sosyal, 0) != exam.getSosyalCount()) {
            errorMessage.append("Sosyal Bilgiler answer count (").append(subjectAnswerCounts.getOrDefault(sosyal, 0)).append(") doesn't match exam question count (").append(exam.getSosyalCount()).append("). ");
        }

        if (subjectAnswerCounts.getOrDefault(din, 0) != exam.getDinCount()) {
            errorMessage.append("Din Kültürü ve Ahlak Bilgisi answer count (").append(subjectAnswerCounts.getOrDefault(din, 0)).append(") doesn't match exam question count (").append(exam.getDinCount()).append("). ");
        }

        if (subjectAnswerCounts.getOrDefault(dil, 0) != exam.getYabanciCount()) {
            errorMessage.append("Yabancı Dil answer count (").append(subjectAnswerCounts.getOrDefault(dil, 0)).append(") doesn't match exam question count (").append(exam.getYabanciCount()).append("). ");
        }

        if (errorMessage.length() > 0) {
            throw new IllegalArgumentException("Validation failed: Answer counts don't match exam question counts. " + errorMessage.toString().trim());
        }
    }

    private void validateSubmittedTopics(List<TopicResultDto> submittedTopicResults) {
        // Fetch all topics related to the subjects of the given exam
        Set<Long> expectedTopicIds = topicRepository.findAll().stream().flatMap(subject -> topicService.getTopicsBySubjectId(subject.getId()).stream()) // Assuming this stream is of TopicDTO
                .map(TopicDTO::getId) // Changed from Topic::getId
                .collect(Collectors.toSet());

        Set<Long> submittedTopicIds = submittedTopicResults.stream().map(TopicResultDto::getTopicId).collect(Collectors.toSet());

        if (submittedTopicIds.size() != submittedTopicResults.size()) {
            throw new IllegalArgumentException("Duplicate topics submitted.");
        }

        if (!expectedTopicIds.equals(submittedTopicIds)) {
            Set<Long> missingTopics = new HashSet<>(expectedTopicIds);
            missingTopics.removeAll(submittedTopicIds);

            Set<Long> extraTopics = new HashSet<>(submittedTopicIds);
            extraTopics.removeAll(expectedTopicIds);

            StringBuilder errorMessage = new StringBuilder("Topic mismatch for the exam. ");
            if (!missingTopics.isEmpty()) {
                errorMessage.append("Missing topics: ").append(missingTopics).append(". ");
            }
            if (!extraTopics.isEmpty()) {
                errorMessage.append("Unexpected topics: ").append(extraTopics).append(".");
            }
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }
    }

    private void validateExamDate(Exam exam) {
        LocalDateTime now = LocalDateTime.now();
        if (exam.getExamDate().isAfter(now)) {
            throw new IllegalArgumentException("Exam date has not arrived yet. You cannot submit results.");
        }
    }

    public List<ExamBriefResultDto> getExamResultsBriefByStudentId(Long studentId) {
        List<ExamResult> examResults = examResultRepository.findByStudentId(studentId);
        return examResults.stream().map(ExamResultConverter::toBriefDto).collect(Collectors.toList());
    }

    public boolean hasStudentTakenExam(Long studentId, Long examId) {
        ExamResultID id = new ExamResultID(studentId, examId);
        return examResultRepository.existsById(id);
    }
}
