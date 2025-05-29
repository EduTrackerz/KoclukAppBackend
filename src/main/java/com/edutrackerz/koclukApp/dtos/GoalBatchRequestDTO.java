package com.edutrackerz.koclukApp.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GoalBatchRequestDTO{
    private Long teacherId;
    private Long studentId;
    private List<SingleGoalDTO> goals;
}
