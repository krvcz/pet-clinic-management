package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.dto.VisitDto;
import pl.ssanko.petclinic.data.entity.Visit;

import java.util.Collection;
import java.util.stream.Stream;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query(value = "SELECT count(1) FROM visits c where  (c.\"date\" - interval '24 hour') <= c.\"date\"", nativeQuery = true)
    Long countLast24Hours();

    @Query(value = "SELECT count(1) FROM visits c where status = :status", nativeQuery = true)
    Long countByStatus(String status);

    @Query(value = "SELECT c FROM Visit c where c.pet.customer.id = :customerId order by c.date desc ")
    Page<Visit> findAllByCustomerId(Pageable pageable, Long customerId);

    @Query(value = "SELECT c FROM Visit c where c.pet.id = :petId order by c.date desc ")
    Page<Visit> findAllByPetId(Pageable pageable, Long petId);

    @Query(value = "SELECT c FROM Visit c where c.status != \"Zakończona\" order by c.date desc")
    Page<Visit> findAllActiveSortedByDate(Pageable pageable);

    @Query(value = "SELECT c FROM Visit c where c.status = \"Zakończona\" order by c.date desc")
    Page<Visit> findAllEndedSortedByDate(Pageable pageable);

    @Query(value = "SELECT c FROM Visit c where c.pet.id = :petId and c.id != :visitId order by c.date desc ")
    Page<Visit> findAllByPetIdAndVisitId(Long petId, Long visitId, Pageable pageable);
}
