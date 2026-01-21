package org.example.medicine_api.repository;

import org.example.medicine_api.model.Medicine;
import org.example.medicine_api.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    boolean existsByNameAndManufacturer(String name, Manufacturer manufacturer);

    List<Medicine> findByExpiryDateBefore(LocalDate expiryDateBefore);

    List<Medicine> findByNameContainingIgnoreCaseAndManufacturer(String name, Manufacturer manufacturer);

    List<Medicine> findByNameContainingIgnoreCase(String name);
}
