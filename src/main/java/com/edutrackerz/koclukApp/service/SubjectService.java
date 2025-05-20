package com.edutrackerz.koclukApp.service;

import com.edutrackerz.koclukApp.converters.SubjectConverter;
import com.edutrackerz.koclukApp.dtos.SubjectDTO;
import com.edutrackerz.koclukApp.entities.Subject;
import com.edutrackerz.koclukApp.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectConverter subjectConverter;

    public SubjectService(SubjectRepository subjectRepository, SubjectConverter subjectConverter) {
        this.subjectRepository = subjectRepository;
        this.subjectConverter = subjectConverter;
    }

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().map(subjectConverter::toDTO).toList();
    }


}
