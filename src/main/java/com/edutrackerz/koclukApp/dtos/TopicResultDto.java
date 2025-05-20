package com.edutrackerz.koclukApp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicResultDto {
    private Long topicId;
    private String topicName;
    // Optional: if you want to show which subject this topic belongs to in the DTO
    private String subjectName; 
    private int correctCount;
    private int wrongCount;
    private int emptyCount;
    private double net; // Calculated: correct - (wrong / 4.0)
}