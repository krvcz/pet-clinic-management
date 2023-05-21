package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;

@Repository
public interface MedicalProcedureRepository extends JpaRepository<MedicalProcedure, Long> {

    public Page<MedicalProcedure> findAllByTypeEquals(String type, Pageable pageable);
}
