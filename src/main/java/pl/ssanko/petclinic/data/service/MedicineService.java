package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.repository.MedicineRepository;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    @Transactional(readOnly = true)
    public Stream<Medicine> getMedicines(){
        return medicineRepository.findAll().stream();
    }

}
