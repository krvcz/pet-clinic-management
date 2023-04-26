package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.repository.PetRepository;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public Stream<Pet> getPets(Pageable pageable) {
        return petRepository.findAll().stream();
    }

    @Transactional(readOnly = true)
    public Stream<Pet> getPetsByCustomer(Pageable pageable, Long customerId) {
        return petRepository.findAllByCustomerId(pageable, customerId).stream();
    }
}
