package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.VisitMedicalProcedure;
import pl.ssanko.petclinic.data.entity.VisitMedicine;

@Repository
public interface VisitsMedicalProceduresRepository extends JpaRepository<VisitMedicalProcedure, VisitMedicalProcedure.VisitMedicalProcedurePK> {

    Page<VisitMedicalProcedure> findVisitMedicalProceduresByVisitId(Long visitId, Pageable pageable);

    void deleteAllByVisitId(Long visitId);

    @Modifying
    @Query(value = "DELETE FROM visits_medical_procedures c " +
                    "WHERE c.visit_id = :visitId AND " +
                    "c.medical_procedure_id in (SELECT id FROM medical_procedures WHERE type = :type)",
            nativeQuery = true)
    void deleteAllByVisitIdAndSpecifyType(@Param("visitId") Long visitId, @Param("type") String type);
}
