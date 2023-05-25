package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.entity.Customer;

import java.util.Collection;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("from Customer c where upper(c.firstName) like concat('%',upper(:query),'%') or " +
            "upper(c.lastName) like concat('%',upper(:query),'%') or " +
            "upper(c.email) like concat('%',upper(:query),'%') or " +
            "upper(c.phoneNumber) like concat('%',upper(:query),'%')"
    )
    Page<Customer> findAllByFilter(Pageable pageable, @Param("query") String filter);

    @Query(
            "select count(c) = 1 " +
            "from Customer c " +
                    "where upper(c.firstName) = upper(:firstName) and " +
                    "upper(c.lastName) = upper(:lastName) and " +
                    "upper(c.email) = upper(:email)"
    )
    boolean isNotUnique(String firstName, String lastName, String email);

    @Query("SELECT DISTINCT new pl.ssanko.petclinic.data.dto.CustomerStatsDto(count(distinct d), count(distinct p)) FROM Customer c " +
            "JOIN Visit d ON c.id = d.pet.customer.id " +
            "JOIN Pet p ON p.customer.id = c.id " +
            "where c.id = :customerId ")
    CustomerStatsDto getCustomerStats(Long customerId);
}
