package com.edutrackerz.koclukApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Exam_Result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult {

    @EmbeddedId
    private ExamResultID id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Column(name = "turkce_correct")
    @Min(value = 0, message = "Turkce correct answers must be zero or positive.")
    private int turkceCorrect;
    @Column(name = "turkce_wrong")
    @Min(value = 0, message = "Turkce wrong answers must be zero or positive.")
    private int turkceWrong;
    @Column(name = "turkce_empty")
    private int turkceEmpty;

    @Column(name = "matematik_correct")
    @Min(value = 0, message = "Matematik correct answers must be zero or positive.")
    private int matematikCorrect;
    @Column(name = "matematik_wrong")
    @Min(value = 0, message = "Matematik wrong answers must be zero or positive.")
    private int matematikWrong;
    @Column(name = "matematik_empty")
    private int matematikEmpty;

    @Column(name = "fen_correct")
    @Min(value = 0, message = "Fen Bilgisi correct answers must be zero or positive.")
    private int fenCorrect;
    @Column(name = "fen_wrong")
    @Min(value = 0, message = "Fen Bilgisi wrong answers must be zero or positive.")
    private int fenWrong;
    @Column(name = "fen_empty")
    private int fenEmpty;

    @Column(name = "sosyal_correct")
    @Min(value = 0, message = "Social Studies correct answers must be zero or positive.")
    private int sosyalCorrect;
    @Column(name = "sosyal_wrong")
    @Min(value = 0, message = "Sosyal Bilgiler wrong answers must be zero or positive.")
    private int sosyalWrong;
    @Column(name = "sosyal_empty")
    private int sosyalEmpty;

    @Column(name = "din_correct")
    @Min(value = 0, message = "Din Kültürü correct answers must be zero or positive.")
    private int dinCorrect;
    @Column(name = "din_wrong")
    @Min(value = 0, message = "Din Kültürü wrong answers must be zero or positive.")
    private int dinWrong;
    @Column(name = "din_empty")
    private int dinEmpty;

    @Column(name = "yabanci_correct")
    @Min(value = 0, message = "Yabanci Dil correct answers must be zero or positive.")
    private int yabanciCorrect;
    @Column(name = "yabanci_wrong")
    @Min(value = 0, message = "Yabanci Dil wrong answers must be zero or positive.")
    private int yabanciWrong;
    @Column(name = "yabanci_empty")
    private int yabanciEmpty;

    @Column(name = "total_net")
    private double totalNet;
}

