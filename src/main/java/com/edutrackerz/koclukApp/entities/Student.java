package com.edutrackerz.koclukApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//bu bir github denemesi
@Entity
@Getter
@Setter
@Table(name = "Student")

@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "student_name")
    @NotEmpty
    private String name;

    @Column(name = "student_username")
    @NotEmpty
    private String username;
}

