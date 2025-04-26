package com.edutrackerz.koclukApp.entities;

import com.edutrackerz.koclukApp.enums.Branch;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(name = "teacher_name")
    @NotEmpty
    private String name;

    @Column(name = "teacher_username")
    @NotEmpty
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "teacher_branch")
    private Branch branch;
} 