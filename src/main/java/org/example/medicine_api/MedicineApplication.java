package org.example.medicine_api;

import org.example.medicine_api.model.Drug;
import org.example.medicine_api.model.Manufacturer;
import org.example.medicine_api.repository.DrugRepository;
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
    CommandLineRunner loadData(DrugRepository repository) {
        return args -> {
            repository.save(new Drug(
                    null,
                    "Paracetamol",
                    Manufacturer.BAYER,
                    LocalDate.now().plusMonths(12),
                    100
            ));

            repository.save(new Drug(
                    null,
                    "Ibuprofen",
                    Manufacturer.PFIZER,
                    LocalDate.now().plusMonths(8),
                    50
            ));

            repository.save(new Drug(
                    null,
                    "Amoxicillin",
                    Manufacturer.NOVARTIS,
                    LocalDate.now().plusMonths(6),
                    30
            ));

            repository.save(new Drug(
                    null,
                    "Aspirin",
                    Manufacturer.BAYER,
                    LocalDate.now().plusMonths(18),
                    200
            ));

            repository.save(new Drug(
                    null,
                    "Vitamin C",
                    Manufacturer.ROCHE,
                    LocalDate.now().plusYears(2),
                    500
            ));
        };
    }

}
