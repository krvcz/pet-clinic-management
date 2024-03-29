package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.MedicalProcedure;

@Repository
public interface MedicalProcedureRepository extends JpaRepository<MedicalProcedure, Long> {

    Page<MedicalProcedure> findAllByTypeEqualsAndActiveTrue(String type, Pageable pageable);

    Page<MedicalProcedure> findAllByActiveTrue(Pageable pageable);

    @Query("from MedicalProcedure c where c.active = true and upper(c.name) like concat('%',upper(:query),'%') or " +
            "upper(c.type) like concat('%',upper(:query),'%') or " +
            "upper(c.description) like concat('%',upper(:query),'%')"
    )
    Page<MedicalProcedure> findAllByFilter(Pageable pageable, @Param("query") String filter);

    @Query(
            "select count(c) = 1 " +
                    "from MedicalProcedure c " +
                    "where upper(c.name) = upper(:name) and c.active = true"
    )
    boolean isNotUnique(String name);
}
