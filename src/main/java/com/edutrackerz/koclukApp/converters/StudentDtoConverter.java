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

    public static Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setUsername(studentDTO.getUsername());
        return student;
    }
}
