package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.TeacherDTO;
import com.edutrackerz.koclukApp.entities.Teacher;

public class TeacherDtoConverter {

    public static TeacherDTO convertToDto(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getUsername(),
                teacher.getBranch()
        );
    }

    public static Teacher convertToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setName(teacherDTO.getName());
        teacher.setUsername(teacherDTO.getUsername());
        teacher.setBranch(teacherDTO.getBranch());
        return teacher;
    }
} 