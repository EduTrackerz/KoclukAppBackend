package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.SubjectDTO;
import com.edutrackerz.koclukApp.entities.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter {
    public SubjectDTO toDTO(Subject s){
        return new SubjectDTO(s.getId(), s.getName());
    }


}
