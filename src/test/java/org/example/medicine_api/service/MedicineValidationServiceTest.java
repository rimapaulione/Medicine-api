package org.example.medicine_api.service;


import org.example.medicine_api.dto.MedicineSaveDto;
import org.example.medicine_api.dto.MedicineUpdateDto;
import org.example.medicine_api.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MedicineValidationServiceTest {


    private MedicineValidationService validator;

    @BeforeEach
    void setUp() {
        validator = new MedicineValidationService();
    }

    @Test
    void test_shouldPassValidateForCreateWithValidData() {
        MedicineSaveDto dto = new MedicineSaveDto();
        dto.setName("Paracetamol");
        dto.setManufacturer("PFIZER");
        dto.setExpiryDate(LocalDate.now().plusDays(10));
        dto.setStock(5);

        assertDoesNotThrow(() -> validator.validateForCreate(dto));
    }

    @Test
    void test_shouldThrowIfNameIsNull() {
        MedicineSaveDto dto = new MedicineSaveDto();
        dto.setName(null);
        dto.setManufacturer("PFIZER");
        dto.setExpiryDate(LocalDate.now().plusDays(10));
        dto.setStock(5);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validateForCreate(dto));

        assertEquals("Name can't be null!", exception.getMessage());
    }

    @Test
    void test_shouldThrowIfManufacturerIsInvalid() {
        MedicineSaveDto dto = new MedicineSaveDto();
        dto.setName("Ibuprofen");
        dto.setManufacturer("INVALID");
        dto.setExpiryDate(LocalDate.now().plusDays(10));
        dto.setStock(5);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validateForCreate(dto));

        assertEquals("Invalid manufacturer value", exception.getMessage());
    }

    @Test
    void test_shouldThrowIfExpiryDateInPast() {
        MedicineSaveDto dto = new MedicineSaveDto();
        dto.setName("Ibuprofen");
        dto.setManufacturer("PFIZER");
        dto.setExpiryDate(LocalDate.now().minusDays(1));
        dto.setStock(5);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validateForCreate(dto));

        assertEquals("Expiry date cannot be in the past", exception.getMessage());
    }

    @Test
    void test_shouldThrowIfStockNegative() {
        MedicineSaveDto dto = new MedicineSaveDto();
        dto.setName("Ibuprofen");
        dto.setManufacturer("PFIZER");
        dto.setExpiryDate(LocalDate.now().plusDays(1));
        dto.setStock(-1);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                validator.validateForCreate(dto));

        assertEquals("Stock cannot be negative or null", exception.getMessage());
    }

    @Test
    void test_shouldPassValidateForDeleteWithZeroStock() {
        assertDoesNotThrow(() -> validator.validateForDelete(0));
    }

    @Test
    void test_shouldThrowValidateForDeleteWithPositiveStock() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validateForDelete(5));
        assertEquals("Medicine can be deleted only when stock is 0", exception.getMessage());
    }

    @Test
    void test_shouldPassValidateForUpdateWithValidData() {
        MedicineUpdateDto dto = new MedicineUpdateDto();
        dto.setName("Ibuprofen");
        dto.setExpiryDate(LocalDate.now().plusDays(5));
        dto.setStock(10);

        assertDoesNotThrow(() -> validator.validateForUpdate(dto));
    }

    @Test
    void test_shouldThrowIfStockNegativeOnUpdate() {
        MedicineUpdateDto dto = new MedicineUpdateDto();
        dto.setName("Ibuprofen");
        dto.setExpiryDate(LocalDate.now().plusDays(5));
        dto.setStock(-2);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> validator.validateForUpdate(dto));

        assertEquals("Stock cannot be negative or null", exception.getMessage());
    }

    @Test
    void test_shouldPassValidateForStockChangePositiveQuantity() {
        assertDoesNotThrow(() -> validator.validateForStockChange(5));
    }

    @Test
    void test_shouldThrowValidateForStockChangeZeroOrNegative() {
        ValidationException exception1 = assertThrows(ValidationException.class,
                () -> validator.validateForStockChange(0));
        assertEquals("Quantity must be positive", exception1.getMessage());

        ValidationException exception2 = assertThrows(ValidationException.class,
                () -> validator.validateForStockChange(-1));
        assertEquals("Quantity must be positive", exception2.getMessage());
    }


}