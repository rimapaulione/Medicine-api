package org.example.medicine_api.service;


import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.DrugResponseDto;
import org.example.medicine_api.model.Drug;
import org.example.medicine_api.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugService {

    @Autowired
    private final DrugRepository drugRepository;
    private final DrugMapper drugMapper;

    public List<DrugResponseDto> getAll() {
        return drugRepository.findAll()
                .stream()
                .map(drugMapper::toDto)
                .toList();
    }
}
