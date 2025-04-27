package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.managerDTO;
import com.edutrackerz.koclukApp.entities.Manager;

public class managerDTOConverter {

    public static managerDTO convertToDto(Manager manager) {
        return new managerDTO(
            manager.getId(),
            manager.getName(),
            manager.getUsername()
        );
    }

    public static Manager convertToEntity(managerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setId(managerDTO.getId());
        manager.setName(managerDTO.getName());
        manager.setUsername(managerDTO.getUsername());
        return manager;
    }

}
