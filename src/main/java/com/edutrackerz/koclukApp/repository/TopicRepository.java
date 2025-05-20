package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByName(String name);

    List<Topic> findBySubjectId(Long subjectId);

    List<Topic> findBySubjectName(String subjectName);

}