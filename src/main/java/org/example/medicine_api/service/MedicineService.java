package org.example.medicine_api.service;


import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.MedicineSaveDto;
import org.example.medicine_api.dto.MedicineResponseDto;
import org.example.medicine_api.dto.MedicineUpdateDto;
import org.example.medicine_api.exception.NotFoundException;
import org.example.medicine_api.exception.ValidationException;
import org.example.medicine_api.model.Medicine;
import org.example.medicine_api.model.Manufacturer;
import org.example.medicine_api.repository.MedicineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository repository;
    private final MedicineMapper mapper;
    private final MedicineValidationService validator;


    public List<MedicineResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Page<MedicineResponseDto> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Medicine> medicines = repository.findAll(pageable);
        return medicines.map(mapper::toDto);
    }

    public MedicineResponseDto getById(final Long id) {
        Medicine medicine = this.findById(id);
        return mapper.toDto(medicine);
    }

    public MedicineResponseDto add(final MedicineSaveDto newMedicine) {

        validator.validateForCreate(newMedicine);

        if (repository.existsByNameAndManufacturer(newMedicine.getName(), Manufacturer.valueOf(newMedicine.getManufacturer()))) {
            throw new ValidationException(
                    "Medicine with same name and manufacturer already exists"
            );
        }
        return mapper.toDto(repository.save(mapper.toEntity(newMedicine)));
    }

    public void deleteById(final Long id) {
        Medicine medicine = this.findById(id);
        validator.validateForDelete(medicine.getStock());
        repository.deleteById(id);
    }

    @Transactional
    public MedicineResponseDto update(final Long id, final MedicineUpdateDto updateMedicine) {

        validator.validateForUpdate(updateMedicine);

        Medicine existing = this.findById(id);

        existing.setName(updateMedicine.getName());
        existing.setExpiryDate(updateMedicine.getExpiryDate());
        existing.setStock(updateMedicine.getStock());

        return mapper.toDto( repository.save(existing));
    }

    @Transactional
    public MedicineResponseDto increase(final Long id, final Integer quantity) {

        validator.validateForStockChange(quantity);

        Medicine medicine = this.findById(id);

        medicine.setStock(medicine.getStock() + quantity);
        return mapper.toDto(repository.save(medicine));
    }

    @Transactional
    public MedicineResponseDto decrease(final Long id, final Integer quantity) {
        validator.validateForStockChange(quantity);

        Medicine medicine = this.findById(id);

        if (medicine.getStock() < quantity) {
            throw new ValidationException("Cannot decrease stock below 0");
        }

        medicine.setStock(medicine.getStock() - quantity);
        return mapper.toDto(repository.save(medicine));
    }

    public Medicine findById(final Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException("Medicine not found with id " + id));
    }
}
