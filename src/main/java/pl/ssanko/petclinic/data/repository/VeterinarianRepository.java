package pl.ssanko.petclinic.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Veterinarian;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

    Page<Veterinarian> findAllByActiveTrue(Pageable pageable);
}
