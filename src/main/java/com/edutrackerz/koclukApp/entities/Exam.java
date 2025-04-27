package com.edutrackerz.koclukApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Exam")

@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Long id;

    @Column(name = "exam_name")
    @NotEmpty(message = "Exam name must not be empty")
    private String name;

    @Column(name = "turkce_count")
    @Min(value = 0, message = "Turkce question count must be non-negative")
    private int turkceCount;

    @Column(name = "matematik_count")
    @Min(value = 0, message = "Matematik question count must be non-negative")
    private int matematikCount;

    @Column(name = "fen_count")
    @Min(value = 0, message = "Fen Bilimleri question count must be non-negative")
    private int fenCount;

    @Column(name = "sosyal_count")
    @Min(value = 0, message = "Sosyal Bilgiler question count must be non-negative")
    private int sosyalCount;

    @Column(name = "din_count")
    @Min(value = 0, message = "Din Kulturu question count must be non-negative")
    private int dinCount;

    @Column(name = "yabanci_count")
    @Min(value = 0, message = "Yabanci Dil question count must be non-negative")
    private int yabanciCount;

    @Column(name = "exam_date")
    private LocalDateTime examDate;
}
