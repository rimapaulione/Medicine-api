package org.example.medicine_api.service;

import org.example.medicine_api.dto.MedicineSaveDto;
import org.example.medicine_api.dto.MedicineUpdateDto;
import org.example.medicine_api.exception.ValidationException;
import org.example.medicine_api.model.Manufacturer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MedicineValidationService {

    public void validateForCreate(MedicineSaveDto dto) {
        this.validateCommonFields(dto.getName(), dto.getExpiryDate(), dto.getStock());

        if (dto.getManufacturer() == null || dto.getManufacturer().isBlank()) {
            throw new ValidationException("Manufacturer can't be null!");
        }
        try {
            Manufacturer.valueOf(dto.getManufacturer().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid manufacturer value");
        }
    }

    public void validateForDelete(Integer stock) {
        if (stock > 0) {
            throw new ValidationException(
                    "Medicine can be deleted only when stock is 0"
            );
        }
    }

    public void validateForUpdate(MedicineUpdateDto dto) {
        this.validateCommonFields(dto.getName(), dto.getExpiryDate(), dto.getStock());
    }

    public void validateForStockChange(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new ValidationException("Quantity must be positive");
        }
    }

    private void validateCommonFields(String name, LocalDate expiryDate, Integer stock) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name can't be null!");
        }
        if (expiryDate == null) {
            throw new ValidationException("Expiry date cannot be null");
        }

        if (expiryDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Expiry date cannot be in the past");
        }
        if (stock == null || stock < 0) {
            throw new ValidationException("Stock cannot be negative or null");
        }
    }
}