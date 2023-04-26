package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Species;
import pl.ssanko.petclinic.data.repository.SpeciesRepository;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    @Transactional(readOnly = true)
    public Stream<Species> getSpecies(Pageable pageable) {
        return speciesRepository.findAll(pageable).stream();
    }

}
