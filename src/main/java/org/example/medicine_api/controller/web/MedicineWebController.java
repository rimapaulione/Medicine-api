package org.example.medicine_api.controller.web;

import lombok.RequiredArgsConstructor;
import org.example.medicine_api.dto.MedicineResponseDto;
import org.example.medicine_api.dto.MedicineSaveDto;
import org.example.medicine_api.dto.MedicineUpdateDto;
import org.example.medicine_api.dto.StockUpdateDto;
import org.example.medicine_api.exception.NotFoundException;
import org.example.medicine_api.exception.ValidationException;
import org.example.medicine_api.model.Manufacturer;
import org.example.medicine_api.service.MedicineService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineWebController {

    private final MedicineService medicineService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<MedicineResponseDto> medicines = medicineService.getAllPaginated(page, size);
        model.addAttribute("medicines", medicines);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("activePage", "medicines");
        return "medicine/list";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String manufacturer,
            Model model) {

        if (name == null || name.isBlank()) {
            return "redirect:/medicines";
        }

        Manufacturer mfr = null;
        if (manufacturer != null && !manufacturer.isBlank()) {
            try {
                mfr = Manufacturer.valueOf(manufacturer);
            } catch (IllegalArgumentException ignored) {
            }
        }

        List<MedicineResponseDto> results = medicineService.searchMedicines(name, mfr);
        model.addAttribute("medicines", results);
        model.addAttribute("searchName", name);
        model.addAttribute("searchManufacturer", manufacturer);
        model.addAttribute("manufacturers", Manufacturer.values());
        model.addAttribute("activePage", "medicines");
        return "medicine/search";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        MedicineResponseDto medicine = medicineService.getById(id);
        model.addAttribute("medicine", medicine);
        model.addAttribute("activePage", "medicines");
        return "medicine/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("medicine", new MedicineSaveDto());
        model.addAttribute("manufacturers", Manufacturer.values());
        model.addAttribute("isEdit", false);
        model.addAttribute("activePage", "medicines");
        return "medicine/form";
    }

    @PostMapping
    public String create(
            @ModelAttribute MedicineSaveDto medicine,
            RedirectAttributes redirectAttributes) {
        try {
            MedicineResponseDto created = medicineService.add(medicine);
            redirectAttributes.addFlashAttribute("successMessage",
                "Medicine '" + created.getName() + "' created successfully!");
            return "redirect:/medicines/" + created.getId();
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/medicines/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        MedicineResponseDto medicine = medicineService.getById(id);

        MedicineUpdateDto updateDto = new MedicineUpdateDto();
        updateDto.setName(medicine.getName());
        updateDto.setExpiryDate(medicine.getExpiryDate());
        updateDto.setStock(medicine.getStock());

        model.addAttribute("medicine", updateDto);
        model.addAttribute("medicineId", id);
        model.addAttribute("currentManufacturer", medicine.getManufacturer());
        model.addAttribute("isEdit", true);
        model.addAttribute("activePage", "medicines");
        return "medicine/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute MedicineUpdateDto medicine,
            RedirectAttributes redirectAttributes) {
        try {
            MedicineResponseDto updated = medicineService.update(id, medicine);
            redirectAttributes.addFlashAttribute("successMessage",
                "Medicine '" + updated.getName() + "' updated successfully!");
            return "redirect:/medicines/" + id;
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/medicines/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            medicineService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Medicine deleted successfully!");
            return "redirect:/medicines";
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/medicines/" + id;
        }
    }

    @PostMapping("/{id}/stock/increase")
    public String increaseStock(
            @PathVariable Long id,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {
        try {
            medicineService.increase(id, quantity);
            redirectAttributes.addFlashAttribute("successMessage",
                "Stock increased by " + quantity + " units!");
            return "redirect:/medicines/" + id;
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/medicines/" + id;
        }
    }

    @PostMapping("/{id}/stock/decrease")
    public String decreaseStock(
            @PathVariable Long id,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {
        try {
            medicineService.decrease(id, quantity);
            redirectAttributes.addFlashAttribute("successMessage",
                "Stock decreased by " + quantity + " units!");
            return "redirect:/medicines/" + id;
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/medicines/" + id;
        }
    }

    @GetMapping("/expired")
    public String expired(Model model) {
        List<MedicineResponseDto> expiredMedicines = medicineService.getAllExpired();
        model.addAttribute("medicines", expiredMedicines);
        model.addAttribute("activePage", "expired");
        return "medicine/expired";
    }

    @GetMapping("/low-stock")
    public String lowStock(
            @RequestParam(defaultValue = "10") int threshold,
            Model model) {
        List<MedicineResponseDto> lowStockMedicines = medicineService.getLowStock(threshold);
        model.addAttribute("medicines", lowStockMedicines);
        model.addAttribute("threshold", threshold);
        model.addAttribute("activePage", "lowstock");
        return "medicine/low-stock";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/medicines";
    }
}
