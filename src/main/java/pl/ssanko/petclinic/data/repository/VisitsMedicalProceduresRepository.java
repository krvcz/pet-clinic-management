package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.VisitMedicalProcedure;
import pl.ssanko.petclinic.data.entity.VisitMedicine;

@Repository
public interface VisitsMedicalProceduresRepository extends JpaRepository<VisitMedicalProcedure, VisitMedicalProcedure.VisitMedicalProcedurePK> {

    Page<VisitMedicalProcedure> findVisitMedicineByVisitId(Long visitId, Pageable pageable);

    void deleteAllByVisitId(Long visitId);
}
