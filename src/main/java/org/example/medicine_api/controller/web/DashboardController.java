package org.example.medicine_api.controller.web;

import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.DashboardStatsDto;
import org.example.medicine_api.dto.MedicineResponseDto;
import org.example.medicine_api.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final MedicineService medicineService;

    @GetMapping("/")
    public String dashboard(Model model) {
        DashboardStatsDto stats = medicineService.getDashboardStats();
        List<MedicineResponseDto> expiredMedicines = medicineService.getAllExpired();
        List<MedicineResponseDto> lowStockMedicines = medicineService.getLowStock(10);

        model.addAttribute("stats", stats);
        model.addAttribute("expiredMedicines", expiredMedicines.stream().limit(5).toList());
        model.addAttribute("lowStockMedicines", lowStockMedicines.stream().limit(5).toList());
        model.addAttribute("activePage", "dashboard");

        return "dashboard/index";
    }
}
