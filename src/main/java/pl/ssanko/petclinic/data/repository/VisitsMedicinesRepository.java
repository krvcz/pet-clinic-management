package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.VisitMedicine;

@Repository
public interface VisitsMedicinesRepository extends JpaRepository<VisitMedicine, VisitMedicine.VisitMedicinePK> {

    Page<VisitMedicine> findVisitMedicineByVisitId(Long visitId, Pageable pageable);

    void deleteAllByVisitId(Long visitId);
}
