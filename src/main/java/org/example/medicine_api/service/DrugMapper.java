package org.example.medicine_api.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.medicine_api.dto.DrugRequestDto;
import org.example.medicine_api.dto.DrugResponseDto;
import org.example.medicine_api.model.Drug;
import org.example.medicine_api.model.Manufacturer;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
public class DrugMapper {

    public DrugResponseDto toDto(Drug drug) {
        DrugResponseDto dto = new DrugResponseDto();
        dto.setId(drug.getId());
        dto.setName(drug.getName());
        dto.setManufacturer(drug.getManufacturer().name());
        dto.setExpiryDate(drug.getExpiryDate());
        dto.setStock(drug.getStock());
        return dto;
    }

    public Drug toEntity(DrugRequestDto drug) {
        Drug entity = new Drug();
        entity.setId(drug.getId());
        entity.setName(drug.getName());
        entity.setManufacturer(Manufacturer.valueOf(drug.getManufacturer()));
        entity.setExpiryDate(drug.getExpiryDate());
        entity.setStock(drug.getStock());
        return entity;
    }
}
