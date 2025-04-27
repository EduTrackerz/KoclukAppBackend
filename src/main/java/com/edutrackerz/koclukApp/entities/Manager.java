package com.edutrackerz.koclukApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    @Column(name = "manager_name")
    @NotEmpty
    private String name;

    @Column(name = "manager_username")
    @NotEmpty
    private String username;
}
