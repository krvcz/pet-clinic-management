package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.dto.PetStatsDto;
import pl.ssanko.petclinic.data.entity.Pet;

import java.util.stream.Stream;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findAllByCustomerId(Pageable pageable, Long customerID);

    @Query("from Pet c where (upper(c.name) like concat('%',upper(:query),'%') or " +
            "upper(c.customer.firstName) like concat('%',upper(:query),'%') or " +
            "upper(c.customer.lastName) like concat('%',upper(:query),'%') or " +
            "upper(c.species.name) like concat('%',upper(:query),'%') or " +
            "upper(c.breed.name) like concat('%',upper(:query),'%')) and " +
            "c.customer.id = :customerId "
    )
    Page<Pet> findAllByCustomerIdFiltered(Pageable pageable, @Param("query") String filter, @Param("customerId") Long customerId);

    @Query(" SELECT new pl.ssanko.petclinic.data.dto.PetStatsDto(count(c)) FROM Visit c " +
            "WHERE c.pet.id = :petId ")
    PetStatsDto getPetStats(Long petId);

}
