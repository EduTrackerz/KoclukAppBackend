package com.edutrackerz.koclukApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Topic_Result", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"exam_result_id", "topic_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "student_id", referencedColumnName = "student_id"),
        @JoinColumn(name = "exam_id", referencedColumnName = "exam_id")
    })

    @JsonIgnore
    private ExamResult examResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private Topic topic;

    @Column(name = "correct_count")
    @Min(value = 0, message = "Correct answers must be non-negative.")
    private int correctCount;

    @Column(name = "wrong_count")
    @Min(value = 0, message = "Wrong answers must be non-negative.")
    private int wrongCount;

    @Column(name = "empty_count")
    @Min(value = 0, message = "Empty answers must be non-negative.")
    private int emptyCount;

    @Transient
    public double getNet() {
        return correctCount - (wrongCount / 4.0);
    }
}