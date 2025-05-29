package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByStudentId(Long studentId);
}
