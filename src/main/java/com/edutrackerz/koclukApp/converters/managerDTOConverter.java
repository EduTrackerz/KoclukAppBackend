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

}
