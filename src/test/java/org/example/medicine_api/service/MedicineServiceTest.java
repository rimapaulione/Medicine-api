package org.example.medicine_api.service;

import org.example.medicine_api.dto.MedicineResponseDto;
import org.example.medicine_api.dto.MedicineSaveDto;
import org.example.medicine_api.dto.MedicineUpdateDto;
import org.example.medicine_api.exception.NotFoundException;
import org.example.medicine_api.exception.ValidationException;
import org.example.medicine_api.model.Manufacturer;
import org.example.medicine_api.model.Medicine;
import org.example.medicine_api.repository.MedicineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock
    private MedicineRepository repository;

    @Mock
    private MedicineMapper mapper;

    @Mock
    private MedicineValidationService validator;

    @InjectMocks
    private MedicineService medicineService;

    private Medicine medicine1;
    private Medicine medicine2;
    private List<Medicine> medicines;
    private MedicineSaveDto saveDto;
    private MedicineResponseDto responseDto;


    @BeforeEach
    void setUp() {
        medicine1 = new Medicine();
        medicine1.setId(1L);
        medicine1.setName("Paracetamol");
        medicine1.setManufacturer(Manufacturer.PFIZER);
        medicine1.setStock(10);
        medicine1.setExpiryDate(LocalDate.now().plusYears(1));

        medicine2 = new Medicine();
        medicine2.setId(2L);
        medicine2.setName("Ibuprofen");
        medicine2.setManufacturer(Manufacturer.PFIZER);
        medicine2.setStock(5);
        medicine2.setExpiryDate(LocalDate.now().plusMonths(6));

        medicines = List.of(medicine1, medicine2);

        saveDto = new MedicineSaveDto();
        saveDto.setName("Paracetamol");
        saveDto.setManufacturer("PFIZER");
        saveDto.setExpiryDate(LocalDate.now().plusYears(1));
        saveDto.setStock(10);

        responseDto = new MedicineResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Paracetamol");
    }


    @Test
    void test_shouldReturnAllMedicines() {

        when(repository.findAll()).thenReturn(medicines);

        MedicineResponseDto dto1 = new MedicineResponseDto();
        dto1.setId(1L);
        dto1.setName("Paracetamol");

        MedicineResponseDto dto2 = new MedicineResponseDto();
        dto2.setId(2L);
        dto2.setName("Ibuprofen");

        when(mapper.toDto(medicine1)).thenReturn(dto1);
        when(mapper.toDto(medicine2)).thenReturn(dto2);

        List<MedicineResponseDto> result = medicineService.getAll();

        assertEquals(2, result.size());
        assertEquals("Paracetamol", result.get(0).getName());
        assertEquals("Ibuprofen", result.get(1).getName());

        verify(repository).findAll();
    }


    @Test
    void test_shouldReturnMedicineByIdObject() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(medicine1));

        MedicineResponseDto dto1 = new MedicineResponseDto();
        dto1.setId(1L);
        dto1.setName("Paracetamol");

        when(mapper.toDto(medicine1)).thenReturn(dto1);
        MedicineResponseDto result = medicineService.getById(1L);

        assertEquals("Paracetamol", dto1.getName());
        verify(repository).findById(1L);
    }

    @Test
    void test_shouldThrowNotFoundWhenMedicineDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> medicineService.getById(1L));

        verify(repository).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    void test_shouldCreateMedicineSuccessfully() {
        when(repository.existsByNameAndManufacturer("Paracetamol", Manufacturer.PFIZER)).thenReturn(false);

        when(mapper.toEntity(saveDto)).thenReturn(medicine1);
        when(repository.save(medicine1)).thenReturn(medicine1);
        when(mapper.toDto(medicine1)).thenReturn(responseDto);

        MedicineResponseDto result = medicineService.add(saveDto);

        assertEquals("Paracetamol", result.getName());

        verify(validator).validateForCreate(saveDto);
        verify(repository).existsByNameAndManufacturer("Paracetamol", Manufacturer.PFIZER);
        verify(repository).save(medicine1);
    }

    @Test
    void test_shouldThrowValidationExceptionWhenMedicineAlreadyExists() {
        when(repository.existsByNameAndManufacturer("Paracetamol", Manufacturer.PFIZER)).thenReturn(true);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> medicineService.add(saveDto)
        );

        assertEquals(
                "Medicine with same name and manufacturer already exists",
                ex.getMessage()
        );

        verify(validator).validateForCreate(saveDto);
        verify(repository, never()).save(any());
    }

    @Test
    void test_shouldThrowValidationExceptionWhenValidationFails() {
        doThrow(new ValidationException("Invalid data"))
                .when(validator).validateForCreate(saveDto);

        assertThrows(
                ValidationException.class,
                () -> medicineService.add(saveDto)
        );

        verify(repository, never()).existsByNameAndManufacturer(any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    void test_shouldDeleteMedicineWhenStockIsZero() {
        medicine1.setStock(0);

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(medicine1));

        medicineService.deleteById(1L);

        verify(repository).findById(1L);
        verify(validator).validateForDelete(0);
        verify(repository).deleteById(1L);
    }

    @Test
    void test_shouldThrowNotFoundExceptionWhenDeletingNonExistingMedicine() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> medicineService.deleteById(1L)
        );

        verify(repository, never()).deleteById(any());
        verify(validator, never()).validateForDelete(anyInt());
    }

    @Test
    void test_shouldThrowValidationExceptionWhenStockIsGreaterThanZero() {
        medicine1.setStock(5);
        when(repository.findById(1L)).thenReturn(Optional.of(medicine1));

        doThrow(new ValidationException("Medicine can be deleted only when stock is 0"))
                .when(validator).validateForDelete(5);

        assertThrows(
                ValidationException.class,
                () -> medicineService.deleteById(1L)
        );

        verify(repository, never()).deleteById(any());
    }

    @Test
    void test_shouldUpdateMedicineSuccessfully() {

        MedicineUpdateDto updateDto = new MedicineUpdateDto();
        updateDto.setName("Updated name");
        updateDto.setExpiryDate(LocalDate.now().plusDays(30));
        updateDto.setStock(20);

        when(repository.findById(1L)).thenReturn(Optional.of(medicine1));
        when(repository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MedicineResponseDto responseDto = new MedicineResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Updated name");

        doReturn(responseDto).when(mapper).toDto(any(Medicine.class));

        MedicineResponseDto result = medicineService.update(1L, updateDto);

        verify(validator).validateForUpdate(updateDto);
        verify(repository).findById(1L);
        verify(repository).save(medicine1);
        verify(mapper).toDto(medicine1);

        assertEquals("Updated name", result.getName());
    }

    @Test
    void test_shouldThrowNotFoundExceptionWhenUpdatingNonExistingMedicine() {
        MedicineUpdateDto updateDto = new MedicineUpdateDto();
        updateDto.setName("Updated name");
        updateDto.setExpiryDate(LocalDate.now().plusDays(30));
        updateDto.setStock(10);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> medicineService.update(1L, updateDto)
        );

        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void test_shouldThrowValidationExceptionWhenUpdateDtoIsInvalid() {
        MedicineUpdateDto updateDto = new MedicineUpdateDto();

        doThrow(new ValidationException("Invalid data"))
                .when(validator).validateForUpdate(updateDto);

        assertThrows(
                ValidationException.class,
                () -> medicineService.update(1L, updateDto)
        );

        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void test_shouldIncreaseStockSuccessfully() {
        int quantityToAdd = 10;
        medicine1.setStock(5);

        when(repository.findById(1L)).thenReturn(Optional.of(medicine1));
        when(repository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDto(any(Medicine.class))).thenReturn(new MedicineResponseDto() {{
            setId(1L);
            setStock(15);
        }});
        MedicineResponseDto result = medicineService.increase(1L, quantityToAdd);

        verify(validator).validateForStockChange(quantityToAdd);
        verify(repository).findById(1L);
        verify(repository).save(medicine1);
        verify(mapper).toDto(medicine1);

        assertEquals(15, result.getStock());
    }

    @Test
    void test_shouldThrowValidationExceptionForInvalidQuantity() {
        int invalidQuantity = -5;

        doThrow(new ValidationException("Invalid quantity"))
                .when(validator).validateForStockChange(invalidQuantity);

        assertThrows(ValidationException.class,
                () -> medicineService.increase(1L, invalidQuantity));

        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void test_shouldDecreaseStockSuccessfully() {
        int quantityToDecrease = 3;
        medicine1.setStock(10);

        when(repository.findById(1L)).thenReturn(Optional.of(medicine1));
        when(repository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDto(any(Medicine.class))).thenReturn(new MedicineResponseDto() {{
            setId(1L);
            setStock(7);
        }});

        MedicineResponseDto result = medicineService.decrease(1L, quantityToDecrease);

        verify(validator).validateForStockChange(quantityToDecrease);
        verify(repository).findById(1L);
        verify(repository).save(any(Medicine.class));
        verify(mapper).toDto(any(Medicine.class));

        assertEquals(7, result.getStock());
    }

    @Test
    void test_shouldThrowWhenDecreasingMoreThanStock() {
        int quantityToDecrease = 15;
        medicine1.setStock(10);

        when(repository.findById(1L)).thenReturn(Optional.of(medicine1));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> medicineService.decrease(1L, quantityToDecrease)
        );

        assertEquals("Cannot decrease stock below 0", exception.getMessage());

        verify(validator).validateForStockChange(quantityToDecrease);
        verify(repository).findById(1L);
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

}