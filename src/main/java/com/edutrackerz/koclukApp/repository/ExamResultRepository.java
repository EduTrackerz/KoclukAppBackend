package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.ExamResult;
import com.edutrackerz.koclukApp.entities.ExamResultID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, ExamResultID> {
    // Bir öğrencinin tüm sınav sonuçlarını listelemek için:
    List<ExamResult> findByStudentId(Long studentId);

    // Belirli bir öğrencinin, belirli bir sınav sonucu kaydını almak için:
    Optional<ExamResult> findByStudentIdAndExamId(Long studentId, Long examId);
}
