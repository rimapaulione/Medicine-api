package org.example.medicine_api.service;


import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.DrugRequestDto;
import org.example.medicine_api.dto.DrugResponseDto;
import org.example.medicine_api.exception.DrugNotFoundException;
import org.example.medicine_api.model.Drug;
import org.example.medicine_api.repository.DrugRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugService {

    private final DrugRepository drugRepository;
    private final DrugMapper drugMapper;
    private final DrugValidator drugValidator;

    public List<DrugResponseDto> getAll() {
        return drugRepository.findAll()
                .stream()
                .map(drugMapper::toDto)
                .toList();
    }

    public DrugResponseDto getById(final Long id) {
        Drug drug = drugRepository.findById(id).orElseThrow(() ->
                new DrugNotFoundException("Drug not found with id " + id));
        return drugMapper.toDto(drug);
    }

    public DrugResponseDto add(final DrugRequestDto newDrug) throws IllegalAccessException {

        drugValidator.validateNewDrug(newDrug);

        Drug entity = drugRepository.save(drugMapper.toEntity(newDrug));
        return drugMapper.toDto(entity);
    }

    public List<DrugResponseDto> deleteById(final Long id) {
        drugRepository.deleteById(id);
        return this.getAll();
    }

    public DrugResponseDto update(DrugRequestDto updateDrug) throws IllegalAccessException {
        drugValidator.validateNewDrug(updateDrug);

        Drug entity = drugRepository.save(drugMapper.toEntity(updateDrug));
        return drugMapper.toDto(entity);
    }
}
