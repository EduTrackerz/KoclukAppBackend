package com.edutrackerz.koclukApp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SingleGoalDTO {
    private Long subjectId;
    private int questionCount;
    private LocalDateTime deadline;
}