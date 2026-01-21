package org.example.medicine_api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalMedicines;
    private int totalStock;
    private long expiredCount;
    private long lowStockCount;
}