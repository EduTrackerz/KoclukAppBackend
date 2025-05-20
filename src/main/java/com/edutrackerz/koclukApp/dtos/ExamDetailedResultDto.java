package com.edutrackerz.koclukApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamDetailedResultDto {
    // This DTO will hold the topic-based distribution of exam results.
    private Map<String, Map<String, TopicStats>> detailedScores; // Subject -> Topic -> TopicStats

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopicStats {
        private int correctAnswers;
        private int incorrectAnswers;
        private int blankAnswers;
    }
}
