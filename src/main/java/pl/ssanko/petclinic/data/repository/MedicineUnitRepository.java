package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;

@Repository
public interface MedicineUnitRepository extends JpaRepository<MedicineUnit, Long> {

    @Query(
            value = "from MedicineUnit c"
    )
    Page<MedicineUnit> findAllWithUnits(Pageable pageable);

    Page<MedicineUnit> findAllByMedicineId(Pageable pageable, Long medicineId);
}
