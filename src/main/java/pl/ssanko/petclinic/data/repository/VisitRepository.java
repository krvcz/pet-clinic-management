package pl.ssanko.petclinic.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("FROM Visit c JOIN fetch c.pet JOIN c.veterinarian WHERE c.id = :visitId")
    public Visit getVisitById(@Param("visitId") Long visitId);

    @Query(value = "SELECT count(1) FROM visits c where  (c.\"date\" - interval '24 hour') <= c.\"date\"", nativeQuery = true)
    Long countLast24Hours();

    @Query(value = "SELECT count(1) FROM visits c where status = :status", nativeQuery = true)
    Long countByStatus(String status);

}
