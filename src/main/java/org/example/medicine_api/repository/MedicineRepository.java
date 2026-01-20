package org.example.medicine_api.repository;

import org.example.medicine_api.model.Medicine;
import org.example.medicine_api.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    boolean existsByNameAndManufacturer(String name, Manufacturer manufacturer);
}
