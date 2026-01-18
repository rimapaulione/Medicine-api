package org.example.medicine_api.controller;


import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.DrugResponseDto;
import org.example.medicine_api.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    @Autowired
    private final DrugService drugService;

    @GetMapping
    public List<DrugResponseDto> getAll(){
        return drugService.getAll();
    }




}
