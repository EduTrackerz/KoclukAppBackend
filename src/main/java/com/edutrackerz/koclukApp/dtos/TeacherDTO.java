package com.edutrackerz.koclukApp.dtos;

import com.edutrackerz.koclukApp.enums.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeacherDTO {
    private Long id;
    private String name;
    private String username;
    private Branch branch;
} 