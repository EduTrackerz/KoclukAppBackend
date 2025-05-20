package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.TopicConverter;
import com.edutrackerz.koclukApp.dtos.TopicDTO;
import com.edutrackerz.koclukApp.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicConverter topicConverter;

    public TopicService(TopicRepository topicRepository, TopicConverter topicConverter) {
        this.topicRepository = topicRepository;
        this.topicConverter = topicConverter;
    }


    public List<TopicDTO> getTopicsBySubjectId(Long subjectId) {
        return topicRepository.findBySubjectId(subjectId).stream().map(topicConverter::toDTO).toList();
    }
}
