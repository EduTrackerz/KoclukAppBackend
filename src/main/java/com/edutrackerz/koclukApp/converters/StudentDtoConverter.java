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

    public static Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setUsername(studentDTO.getUsername());
        return student;
    }
}
