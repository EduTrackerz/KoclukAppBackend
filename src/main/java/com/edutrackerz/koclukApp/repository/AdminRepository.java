package com.edutrackerz.koclukApp.repository;

import com.edutrackerz.koclukApp.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    Optional<Admin> findByUsername(String name);
}
