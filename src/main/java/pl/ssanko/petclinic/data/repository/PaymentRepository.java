package pl.ssanko.petclinic.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
