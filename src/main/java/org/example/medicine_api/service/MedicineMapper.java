package org.example.medicine_api.service;

import org.example.medicine_api.dto.MedicineSaveDto;
import org.example.medicine_api.dto.MedicineResponseDto;
import org.example.medicine_api.model.Medicine;
import org.example.medicine_api.model.Manufacturer;
import org.springframework.stereotype.Component;

@Component
public class MedicineMapper {

    public MedicineResponseDto toDto(Medicine drug) {
        MedicineResponseDto dto = new MedicineResponseDto();
        dto.setId(drug.getId());
        dto.setName(drug.getName());
        dto.setManufacturer(drug.getManufacturer().name());
        dto.setExpiryDate(drug.getExpiryDate());
        dto.setStock(drug.getStock());
        return dto;
    }

    public Medicine toEntity(MedicineSaveDto dto) {
        Medicine entity = new Medicine();
        entity.setName(dto.getName());

        try {
            entity.setManufacturer(
                    Manufacturer.valueOf(dto.getManufacturer().toUpperCase())
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid manufacturer: " + dto.getManufacturer()
            );
        }

        entity.setExpiryDate(dto.getExpiryDate());
        entity.setStock(dto.getStock());
        return entity;
    }
}
