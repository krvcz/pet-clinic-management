package pl.ssanko.petclinic.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Breed;
import pl.ssanko.petclinic.data.entity.Species;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long>{
}


