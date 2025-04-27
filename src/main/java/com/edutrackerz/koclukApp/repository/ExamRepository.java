package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByOrderByExamDateAsc();
}
