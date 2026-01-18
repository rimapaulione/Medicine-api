package org.example.medicine_api.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class DrugRequestDto {
    private Long id;
    private String name;
    private String manufacturer;
    private LocalDate expiryDate;
    private Integer stock;
}
