package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.StudentDTO;
import com.edutrackerz.koclukApp.entities.Student;

public class StudentDtoConverter {

    public static StudentDTO convertToDto(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getUsername()
        );
    }
}
