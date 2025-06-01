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
public class ExamResultRequestDTO {

    private Map<String, GeneralResult> generalResults;
    private Map<String, Map<Long, TopicWrongOnlyWrapper>> detailedScores;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneralResult {
        private int correct;
        private int wrong;
        private int empty;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopicWrongOnlyWrapper {
        private int incorrectAnswers;
    }
}
