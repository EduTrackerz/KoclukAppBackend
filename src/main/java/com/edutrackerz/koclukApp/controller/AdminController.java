package com.edutrackerz.koclukApp.controller;


import com.edutrackerz.koclukApp.converters.adminDTOConverter;
import com.edutrackerz.koclukApp.dtos.adminDTO;
import com.edutrackerz.koclukApp.entities.Admin;
import com.edutrackerz.koclukApp.repository.AdminRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController@RequestMapping("/admins")

public class AdminController {

    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("/getbyid")
    public ResponseEntity<adminDTO> getById(@RequestParam Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            return ResponseEntity.ok(adminDTOConverter.convertToDto(admin.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

     @PostMapping("/register")
    public ResponseEntity<adminDTO> register(@RequestBody adminDTO AdminDTO) {
        Admin admin = adminDTOConverter.convertToEntity(AdminDTO);
        Admin saved = adminRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDTOConverter.convertToDto(saved));
    }

    @GetMapping("/getbyusername")
    public ResponseEntity<adminDTO> getByUsername(@RequestParam String username ) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return ResponseEntity.ok(adminDTOConverter.convertToDto(admin.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
