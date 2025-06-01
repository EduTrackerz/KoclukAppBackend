package com.edutrackerz.koclukApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamDetailedResultDto {

    private Map<String, List<TopicWrongOnly>> detailedScores;
    // subjectKey → list of topic + wrongCount

    private String examName;
    private LocalDateTime examDate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopicWrongOnly {
        private String topicName;
        private int wrongCount;
    }
}
