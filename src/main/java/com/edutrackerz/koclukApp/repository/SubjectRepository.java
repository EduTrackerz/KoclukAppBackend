package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject,Long> {


}
