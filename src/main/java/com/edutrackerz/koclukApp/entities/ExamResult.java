package com.edutrackerz.koclukApp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @OneToMany(mappedBy = "examResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<TopicResult> topicResults = new HashSet<>();

    // ----- Ders bazlı doğru/yanlış/boş sayıları -----

    @Column(name = "turkce_correct", nullable = false) private int turkceCorrect;
    @Column(name = "turkce_wrong", nullable = false) private int turkceWrong;
    @Column(name = "turkce_empty", nullable = false) private int turkceEmpty;

    @Column(name = "matematik_correct", nullable = false) private int matematikCorrect;
    @Column(name = "matematik_wrong", nullable = false) private int matematikWrong;
    @Column(name = "matematik_empty", nullable = false) private int matematikEmpty;

    @Column(name = "fen_correct", nullable = false) private int fenCorrect;
    @Column(name = "fen_wrong", nullable = false) private int fenWrong;
    @Column(name = "fen_empty", nullable = false) private int fenEmpty;

    @Column(name = "sosyal_correct", nullable = false) private int sosyalCorrect;
    @Column(name = "sosyal_wrong", nullable = false) private int sosyalWrong;
    @Column(name = "sosyal_empty", nullable = false) private int sosyalEmpty;

    @Column(name = "din_correct", nullable = false) private int dinCorrect;
    @Column(name = "din_wrong", nullable = false) private int dinWrong;
    @Column(name = "din_empty", nullable = false) private int dinEmpty;

    @Column(name = "yabanci_correct", nullable = false) private int yabanciCorrect;
    @Column(name = "yabanci_wrong", nullable = false) private int yabanciWrong;
    @Column(name = "yabanci_empty", nullable = false) private int yabanciEmpty;

    // ----- Toplam net hesaplama -----

    @Transient
    public double getTotalNet() {
        return calculateNet(turkceCorrect, turkceWrong)
                + calculateNet(matematikCorrect, matematikWrong)
                + calculateNet(fenCorrect, fenWrong)
                + calculateNet(sosyalCorrect, sosyalWrong)
                + calculateNet(dinCorrect, dinWrong)
                + calculateNet(yabanciCorrect, yabanciWrong);
    }

    private double calculateNet(int correct, int wrong) {
        return correct - (wrong / 4.0);
    }
}
