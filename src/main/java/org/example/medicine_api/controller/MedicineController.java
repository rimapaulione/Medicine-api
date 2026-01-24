package org.example.medicine_api.controller;


import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.DashboardStatsDto;
import org.example.medicine_api.dto.MedicineSaveDto;
import org.example.medicine_api.dto.MedicineResponseDto;
import org.example.medicine_api.dto.MedicineUpdateDto;
import org.example.medicine_api.dto.StockUpdateDto;
import org.example.medicine_api.model.Manufacturer;
import org.example.medicine_api.service.MedicineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {
    @Autowired
    private final MedicineService medicineService;

    @GetMapping
    public List<MedicineResponseDto> getAll() {
        return medicineService.getAll();
    }

    @GetMapping("/pages")
    public Page<MedicineResponseDto> getAllWithPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return medicineService.getAllPaginated(page, size);
    }

    @GetMapping("/expired")
    public List<MedicineResponseDto> getAllExpired() {
        return medicineService.getAllExpired();
    }

    @GetMapping("/low-stock")
    public List<MedicineResponseDto> getLowStock(@RequestParam(defaultValue = "10") int stockValue) {
        return medicineService.getLowStock(stockValue);
    }

    @GetMapping("/stats")
    public DashboardStatsDto getDashboardStats() {
        return medicineService.getDashboardStats();
    }

    @GetMapping("/search")
    public List<MedicineResponseDto> search(@RequestParam String name,
                                            @RequestParam(required = false) Manufacturer manufacturer) {
        return medicineService.searchMedicines(name, manufacturer);
    }

    @GetMapping("/{id}")
    public MedicineResponseDto getById(@PathVariable final Long id) {
        return medicineService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicineResponseDto add(@RequestBody final MedicineSaveDto newMedicine) {
        return medicineService.add(newMedicine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        medicineService.deleteById(id);
    }

    @PutMapping("/{id}")
    public MedicineResponseDto update(@PathVariable final Long id, @RequestBody final MedicineUpdateDto updateDrug) {
        return medicineService.update(id, updateDrug);
    }

    @PatchMapping("/{id}/increase")
    public MedicineResponseDto increase(@PathVariable final Long id, @RequestBody final StockUpdateDto dto) {
        return medicineService.increase(id, dto.getQuantity());
    }

    @PatchMapping("/{id}/decrease")
    public MedicineResponseDto decrease(@PathVariable final Long id, @RequestBody final StockUpdateDto dto) {
        return medicineService.decrease(id, dto.getQuantity());
    }
}
