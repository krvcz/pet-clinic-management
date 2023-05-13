package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Veterinarian;
import pl.ssanko.petclinic.data.repository.VeterinarianRepository;


import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;


    @Transactional(readOnly = true)
    public Stream<Veterinarian> getAllVeterinarians(Pageable pageable) {
        return veterinarianRepository.findAll(pageable).stream();
    }



}
