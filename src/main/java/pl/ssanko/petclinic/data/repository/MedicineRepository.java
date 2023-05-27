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
                    "from Medicine c " +
                    "where upper(c.name) = upper(:name) and " +
                    "upper(c.manufacturer) = upper(:manufacturer) and " +
                    "c.active = true"
    )
    boolean isNotUnique(String name, String manufacturer);

    @Query("from Medicine c where c.active = true and (upper(c.name) like concat('%',upper(:query),'%') or " +
            "upper(c.contraindications) like concat('%',upper(:query),'%') or " +
            "upper(c.dosage) like concat('%',upper(:query),'%') or " +
            "upper(c.composition) like concat('%',upper(:query),'%') or " +
            "upper(c.manufacturer) like concat('%',upper(:query),'%') or " +
            "upper(c.administrationRoute) like concat('%',upper(:query),'%')) " +
            "order by c.id desc "
    )
    Page<Medicine> findAllByFilter(Pageable pageable, @Param("query") String filter);

    @Query("from Medicine c  where c.active = true " +
            "order by c.id desc "
    )
    Page<Medicine> findAllOrderedById(Pageable pageable);

    @Query("from Medicine c where c.active = true and size(c.medicineUnits) > 0 and (upper(c.name) like concat('%',upper(:query),'%') or " +
            "upper(c.contraindications) like concat('%',upper(:query),'%') or " +
            "upper(c.dosage) like concat('%',upper(:query),'%') or " +
            "upper(c.composition) like concat('%',upper(:query),'%') or " +
            "upper(c.manufacturer) like concat('%',upper(:query),'%') or " +
            "upper(c.administrationRoute) like concat('%',upper(:query),'%')) " +
            "order by c.id desc "
    )
    Page<Medicine> findAllByFilterWithUnits(Pageable pageable, @Param("query") String filter);

    @Query("from Medicine c  where c.active = true and size(c.medicineUnits) > 0 " +
            "order by c.id desc "
    )
    Page<Medicine> findAllOrderedByIdWithUnits(Pageable pageable);
}
