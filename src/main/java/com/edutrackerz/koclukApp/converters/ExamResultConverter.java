package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.ExamBriefResultDto;
import com.edutrackerz.koclukApp.dtos.ExamDetailedResultDto;
import com.edutrackerz.koclukApp.entities.ExamResult;
import com.edutrackerz.koclukApp.entities.TopicResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:subjects.properties")
public class ExamResultConverter {
    @Autowired
    private Environment env;
    
    // Statik değişkenlerin bildirimi
    public static String turkce;
    public static String matematik;
    public static String fen;
    public static String sosyal;
    public static String din;
    public static String dil;
    
    // Spring uygulaması başladıktan sonra çalışacak ve statik değişkenlere değer atayacak metod
    @PostConstruct
    public void init() {
        turkce = env.getProperty("subject.turkce");
        matematik = env.getProperty("subject.matematik");
        fen = env.getProperty("subject.fen");
        sosyal = env.getProperty("subject.sosyal");
        din = env.getProperty("subject.din");
        dil = env.getProperty("subject.dil");
    }

    public static ExamBriefResultDto toBriefDto(ExamResult result) {
        Map<String, int[]> subjectStats = new HashMap<>();
        if (result.getTopicResults() != null) {
            for (TopicResult tr : result.getTopicResults()) {
                if (tr.getTopic() != null && tr.getTopic().getSubject() != null && tr.getTopic().getSubject().getName() != null) {
                    String subjectName = tr.getTopic().getSubject().getName();
                    int[] counts = subjectStats.getOrDefault(subjectName, new int[]{0, 0, 0}); // 0: correct, 1: wrong, 2: empty
                    counts[0] += tr.getCorrectCount();
                    counts[1] += tr.getWrongCount();
                    counts[2] += tr.getEmptyCount();
                    subjectStats.put(subjectName, counts);
                }
            }
        }

        ExamBriefResultDto dto = new ExamBriefResultDto();
        if (result.getExam() != null) {
            dto.setExamName(result.getExam().getName());
            dto.setExamDate(result.getExam().getExamDate());
            dto.setExamId(result.getExam().getId());
        }        // Set subject-specific fields
        if (subjectStats.containsKey(turkce)) {
            int[] t = subjectStats.get(turkce);
            dto.setTurkceCorrect(t[0]);
            dto.setTurkceWrong(t[1]);
            dto.setTurkceEmpty(t[2]);
        }
        if (subjectStats.containsKey(matematik)) {
            int[] m = subjectStats.get(matematik);
            dto.setMatematikCorrect(m[0]);
            dto.setMatematikWrong(m[1]);
            dto.setMatematikEmpty(m[2]);
        }
        // Ensure subject names here match exactly what's stored in your database for Subject.name
        if (subjectStats.containsKey(fen)) {
            int[] f = subjectStats.get(fen);
            dto.setFenCorrect(f[0]);
            dto.setFenWrong(f[1]);
            dto.setFenEmpty(f[2]);
        }
        if (subjectStats.containsKey(sosyal)) {
            int[] s = subjectStats.get(sosyal);
            dto.setSosyalCorrect(s[0]);
            dto.setSosyalWrong(s[1]);
            dto.setSosyalEmpty(s[2]);
        }
        if (subjectStats.containsKey(din)) {
            int[] d = subjectStats.get(din);
            dto.setDinCorrect(d[0]);
            dto.setDinWrong(d[1]);
            dto.setDinEmpty(d[2]);
        }
        if (subjectStats.containsKey(dil)) {
            int[] y = subjectStats.get(dil);
            dto.setYabanciCorrect(y[0]);
            dto.setYabanciWrong(y[1]);
            dto.setYabanciEmpty(y[2]);
        }

        if (result.getTopicResults() != null) {
            dto.setTotalNet(result.getTopicResults().stream().mapToDouble(TopicResult::getNet).sum());
        } else {
            dto.setTotalNet(0.0);
        }
        return dto;
    }

    public static ExamDetailedResultDto toDetailedDto(ExamResult result) {
        Map<String, Map<String, ExamDetailedResultDto.TopicStats>> detailedScores = new HashMap<>();
        if (result.getTopicResults() != null) {
            for (TopicResult tr : result.getTopicResults()) {
                if (tr.getTopic() != null && tr.getTopic().getSubject() != null ) {
                    String subjectName = tr.getTopic().getSubject().getName();
                    String topicName = tr.getTopic().getName();

                    // O ders için bir map yoksa oluştur
                    if (!detailedScores.containsKey(subjectName)) {
                        detailedScores.put(subjectName, new HashMap<>());
                    }

                    // Statları oluştur
                    ExamDetailedResultDto.TopicStats stats = new ExamDetailedResultDto.TopicStats();
                    stats.setCorrectAnswers(tr.getCorrectCount());
                    stats.setIncorrectAnswers(tr.getWrongCount());
                    stats.setBlankAnswers(tr.getEmptyCount());

                    // map'e ekle
                    detailedScores.get(subjectName).put(topicName, stats);
                }
            }
        }
        ExamDetailedResultDto dto = new ExamDetailedResultDto();
        dto.setDetailedScores(detailedScores);
        dto.setExamName(result.getExam().getName());
        dto.setExamDate(result.getExam().getExamDate());
        return dto;
    }

}