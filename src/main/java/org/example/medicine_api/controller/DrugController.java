package org.example.medicine_api.controller;


import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.DrugRequestDto;
import org.example.medicine_api.dto.DrugResponseDto;
import org.example.medicine_api.service.DrugService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @GetMapping
    public List<DrugResponseDto> getAll() {
        return drugService.getAll();
    }

    @GetMapping("/{id}")
    public DrugResponseDto getById(@PathVariable final Long id) {
        return drugService.getById(id);
    }

    @PostMapping
    public DrugResponseDto add(@RequestBody final DrugRequestDto drug) throws IllegalAccessException {
        return drugService.add(drug);
    }

    @DeleteMapping("/{id}")
    public List<DrugResponseDto> delete(@PathVariable final Long id){
        return drugService.deleteById(id);
    }

    @PutMapping()
    public DrugResponseDto update( @RequestBody final DrugRequestDto updateDrug) throws IllegalAccessException {
        return drugService.update(updateDrug);
    }
}
