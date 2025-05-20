package com.edutrackerz.koclukApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultDto { // This DTO is for SUBMITTING results

    private Long studentId;
    private Long examId;

    private List<TopicResultDto> topicResults; // For detailed topic-based submission

    // totalNet, examName, examDate are not part of this submission DTO.
    // totalNet will be calculated in the backend.
    // examName and examDate are for display and will be in response DTOs.
}