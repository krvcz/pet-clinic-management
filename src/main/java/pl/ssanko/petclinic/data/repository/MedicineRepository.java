package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;

import java.util.Set;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Query(
            value = "select count(c) = 1 " +
                    "from MedicineUnit c " +
                    "where upper(c.medicine.name) = upper(:name) and " +
                    "upper(c.medicine.manufacturer) = upper(:manufacturer) and " +
                    "c.unit IN :units"
    )
    boolean isNotUnique(String name, String manufacturer, Set<String> units);

    @Query("from Medicine c where upper(c.name) like concat('%',upper(:query),'%') or " +
            "upper(c.contraindications) like concat('%',upper(:query),'%') or " +
            "upper(c.dosage) like concat('%',upper(:query),'%') or " +
            "upper(c.composition) like concat('%',upper(:query),'%') or " +
            "upper(c.manufacturer) like concat('%',upper(:query),'%') or " +
            "upper(c.administrationRoute) like concat('%',upper(:query),'%')"
    )
    Page<Medicine> findAllByFilter(Pageable pageable, @Param("query") String filter);

}
