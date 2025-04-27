package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
    Optional<Manager> findByUsername(String name);
}
