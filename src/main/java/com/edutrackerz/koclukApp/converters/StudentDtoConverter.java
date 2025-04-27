package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.StudentDTO;
import com.edutrackerz.koclukApp.entities.Student;

public class StudentDtoConverter {

    public static StudentDTO convertToDto(Student saved) {
        return new StudentDTO(
                saved.getId(),
                saved.getName(),
                saved.getUsername()
        );
    }
}
