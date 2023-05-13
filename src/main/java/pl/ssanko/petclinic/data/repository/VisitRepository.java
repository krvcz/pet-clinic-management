package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Visit;

import java.util.Collection;
import java.util.stream.Stream;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("FROM Visit c JOIN fetch c.pet JOIN c.veterinarian WHERE c.id = :visitId")
    public Visit getVisitById(@Param("visitId") Long visitId);

}
