package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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
                    "where upper(c.firstName) = :firstName and " +
                    "upper(c.lastName) = :lastName and " +
                    "upper(c.email) = :email"
    )
    boolean isNotUnique(String firstName, String lastName, String email);
}
