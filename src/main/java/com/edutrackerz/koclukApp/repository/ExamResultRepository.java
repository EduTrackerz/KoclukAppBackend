package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.ExamResult;
import com.edutrackerz.koclukApp.entities.ExamResultID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExamResultRepository extends JpaRepository<ExamResult, ExamResultID> {
}