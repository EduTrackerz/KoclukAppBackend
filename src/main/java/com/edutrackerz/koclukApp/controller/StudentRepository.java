package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
