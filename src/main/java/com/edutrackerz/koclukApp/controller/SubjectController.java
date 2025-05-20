package com.edutrackerz.koclukApp.controller;

import com.edutrackerz.koclukApp.dtos.SubjectDTO;
import com.edutrackerz.koclukApp.dtos.TopicDTO;
import com.edutrackerz.koclukApp.service.SubjectService;
import com.edutrackerz.koclukApp.service.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final TopicService topicService;


    public SubjectController(SubjectService subjectService, TopicService topicService) {
        this.subjectService = subjectService;
        this.topicService = topicService;
    }

    @GetMapping("/all")
    public List<SubjectDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{subjectId}/topics")
    public List<TopicDTO> getTopicsBySubject(@PathVariable Long subjectId) {
        return topicService.getTopicsBySubjectId(subjectId);
    }

}
