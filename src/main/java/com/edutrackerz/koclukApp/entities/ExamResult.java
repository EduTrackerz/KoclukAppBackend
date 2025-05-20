package com.edutrackerz.koclukApp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @OneToMany(mappedBy = "examResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // To handle Jackson serialization
    private Set<TopicResult> topicResults = new HashSet<>();

    @Transient
    public double getTotalNet() {
        if (this.topicResults == null || this.topicResults.isEmpty()) {
            return 0.0;
        }
        return this.topicResults.stream()
                .mapToDouble(TopicResult::getNet)
                .sum();
    }
}

