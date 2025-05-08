package com.edutrackerz.koclukApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private Long id;
    private String name;
    private int turkceCount;
    private int matematikCount;
    private int fenCount;
    private int sosyalCount;
    private int dinCount;
    private int yabanciCount;
    private LocalDateTime examDate;
} 