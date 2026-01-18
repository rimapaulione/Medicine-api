package org.example.medicine_api.service;

import org.example.medicine_api.dto.DrugRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DrugValidator {

    public void validateNewDrug(DrugRequestDto dto) throws IllegalAccessException {
        if (dto.getName()==null||dto.getName().isBlank()){
            throw new IllegalAccessException("Name can't be null!");
        }
        if (dto.getManufacturer()==null||dto.getManufacturer().isBlank()){
            throw new IllegalAccessException("Manufacturer can't be null!");
        }
        if (dto.getExpiryDate().isBefore(LocalDate.now()) || dto.getExpiryDate()==null){
            throw new IllegalAccessException("Expiry can't be wrong!");
        }
        if (dto.getStock()< 0){
            throw new IllegalAccessException("Stock can't be negative!");
        }

    }


}