package org.example.medicine_api.repository;

import org.example.medicine_api.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DrugRepository extends JpaRepository<Drug, Long> {
}
