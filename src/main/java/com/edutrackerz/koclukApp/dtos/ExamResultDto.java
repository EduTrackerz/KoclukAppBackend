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
public class ExamResultDto {

    private int turkceCorrect;
    private int turkceWrong;
    private int turkceEmpty;

    private int matematikCorrect;
    private int matematikWrong;
    private int matematikEmpty;

    private int fenCorrect;
    private int fenWrong;
    private int fenEmpty;

    private int sosyalCorrect;
    private int sosyalWrong;
    private int sosyalEmpty;

    private int dinCorrect;
    private int dinWrong;
    private int dinEmpty;

    private int yabanciCorrect;
    private int yabanciWrong;
    private int yabanciEmpty;

    private double totalNet;

    private String examName;
    private LocalDateTime examDate;
} 