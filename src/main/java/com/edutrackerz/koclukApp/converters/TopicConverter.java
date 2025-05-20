package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.TopicDTO;
import com.edutrackerz.koclukApp.entities.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicConverter {
    public TopicDTO toDTO(Topic t){
        return new TopicDTO(t.getId(), t.getName());
    }


}
