package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.MedicineUnit;
import pl.ssanko.petclinic.data.entity.VisitMedicine;
import pl.ssanko.petclinic.data.repository.MedicineRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicinesRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    private final VisitsMedicinesRepository visitsMedicinesRepository;

    @Transactional(readOnly = true)
    public Stream<Medicine> getMedicines(Pageable pageable){
        return medicineRepository.findAll(pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<Medicine> getMedicinesAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicinesRepository.findVisitMedicineByVisitId(visitId, pageable).stream().map(VisitMedicine::getMedicine);
    }

    @Transactional(readOnly = true)
    public Map<Long, MedicineUnit> getMedicineUnitsAssignToMedicineAndVisit(Pageable pageable, Long visitId) {
        return visitsMedicinesRepository.findVisitMedicineByVisitId(visitId, pageable).stream().collect(Collectors.toMap(e -> e.getMedicine().getId(), VisitMedicine::getMedicineUnit));
    }

    @Transactional(readOnly = true)
    public Map<Long, BigDecimal> getMedicineQuantityAssignToMedicineAndVisit(Pageable pageable, Long visitId) {
        return visitsMedicinesRepository.findVisitMedicineByVisitId(visitId, pageable).stream().collect(Collectors.toMap(e -> e.getMedicine().getId(), VisitMedicine::getQuantity));
    }
}
