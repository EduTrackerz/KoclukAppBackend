package com.edutrackerz.koclukApp.controller;


import com.edutrackerz.koclukApp.converters.managerDTOConverter;
import com.edutrackerz.koclukApp.dtos.managerDTO;
import com.edutrackerz.koclukApp.entities.Manager;
import com.edutrackerz.koclukApp.repository.ManagerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerRepository managerRepository;

    public ManagerController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @GetMapping("/getbyid")
    public ResponseEntity<managerDTO> getById(@RequestParam Long id) {
        Optional<Manager> manager = managerRepository.findById(id);
        if (manager.isPresent()) {
            return ResponseEntity.ok(managerDTOConverter.convertToDto(manager.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

     @PostMapping("/register")
    public ResponseEntity<managerDTO> register(@RequestBody managerDTO ManagerDTO) {
        Manager manager = managerDTOConverter.convertToEntity(ManagerDTO);
        Manager saved = managerRepository.save(manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(managerDTOConverter.convertToDto(saved));
    }

    @GetMapping("/getbyusername")
    public ResponseEntity<managerDTO> getByUsername(@RequestParam String username ) {
        Optional<Manager> manager = managerRepository.findByUsername(username);
        if (manager.isPresent()) {
            return ResponseEntity.ok(managerDTOConverter.convertToDto(manager.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
