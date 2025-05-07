package com.edutrackerz.koclukApp.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ExamResultID implements Serializable {
    // ExamResult icin composite Primary Key
    private Long studentId;
    private Long examId;
}

