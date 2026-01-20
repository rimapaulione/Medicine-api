package org.example.medicine_api;

import org.example.medicine_api.model.Medicine;
import org.example.medicine_api.model.Manufacturer;
import org.example.medicine_api.repository.MedicineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineApplication.class, args);
    }


    @Bean
    CommandLineRunner loadData(MedicineRepository repository) {
        return args -> {
            repository.save(new Medicine(
                    null,
                    "Paracetamol",
                    Manufacturer.BAYER,
                    LocalDate.now().plusMonths(12),
                    0
            ));

            repository.save(new Medicine(
                    null,
                    "Ibuprofen",
                    Manufacturer.PFIZER,
                    LocalDate.now().plusMonths(8),
                    50
            ));

            repository.save(new Medicine(
                    null,
                    "Amoxicillin",
                    Manufacturer.NOVARTIS,
                    LocalDate.now().plusMonths(6),
                    30
            ));

            repository.save(new Medicine(
                    null,
                    "Aspirin",
                    Manufacturer.BAYER,
                    LocalDate.now().plusMonths(18),
                    200
            ));

            repository.save(new Medicine(
                    null,
                    "Vitamin C",
                    Manufacturer.ROCHE,
                    LocalDate.now().plusYears(2),
                    500
            ));
        };
    }

}
