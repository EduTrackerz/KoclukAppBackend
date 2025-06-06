package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String name);

}
