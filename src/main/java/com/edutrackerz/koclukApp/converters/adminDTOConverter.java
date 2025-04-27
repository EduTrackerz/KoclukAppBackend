package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.adminDTO;
import com.edutrackerz.koclukApp.entities.Admin;

public class adminDTOConverter {

    public static adminDTO convertToDto(Admin admin) {
        return new adminDTO(
            admin.getId(),
            admin.getName(),
            admin.getUsername()
        );
    }

    public static Admin convertToEntity(adminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setId(adminDTO.getId());
        admin.setName(adminDTO.getName());
        admin.setUsername(adminDTO.getUsername());
        return admin;
    }

}
