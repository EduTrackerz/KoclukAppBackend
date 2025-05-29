package com.edutrackerz.koclukApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalResponseDTO {
    private Long id;
    private String subjectName;
    private int questionCount;
    private LocalDateTime deadline;
    private String teacherName;
}
