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
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ValidationException("Name can't be null!");
        }
        if (dto.getManufacturer() == null || dto.getManufacturer().isBlank()) {
            throw new ValidationException("Manufacturer can't be null!");
        }

        try {
            Manufacturer.valueOf(dto.getManufacturer().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid manufacturer value");
        }

        if (dto.getExpiryDate() == null) {
            throw new ValidationException("Expiry date cannot be null");
        }

        if (dto.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Expiry date cannot be in the past");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new ValidationException("Stock cannot be negative or null");
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
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ValidationException("Name can't be null!");
        }

        if (dto.getExpiryDate() == null) {
            throw new ValidationException("Expiry date cannot be null");
        }

        if (dto.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Expiry date cannot be in the past");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new ValidationException("Stock cannot be negative or null");
        }
    }

    public void validateForStockChange(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new ValidationException("Quantity must be positive");
        }
    }
}