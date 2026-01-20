package org.example.medicine_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicineUpdateDto {

    private Integer stock;
    private String name;
    private LocalDate expiryDate;
}
