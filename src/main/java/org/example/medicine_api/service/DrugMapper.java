package org.example.medicine_api.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.medicine_api.dto.DrugResponseDto;
import org.example.medicine_api.model.Drug;
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
}
