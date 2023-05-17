package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Medicine;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.entity.VisitMedicine;
import pl.ssanko.petclinic.data.repository.VisitRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicinesRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;

    private final VisitsMedicinesRepository visitsMedicinesRepository;

    @Transactional(readOnly = true)
    public Stream<Visit> getSortedVisits(Pageable pageable) {
        return visitRepository.findAll(pageable).stream();
    }


    @Transactional(readOnly = true)
    public Visit getVisitById(long visitId) {
        return visitRepository.findById(visitId).get();
//        return visitRepository.getById(visitId);

    }

    @Transactional
    public Visit addNewVisit(Visit visit) {
        Visit newVisit = new Visit();

        newVisit.setStatus("W trakcie");
        newVisit.setPet(visit.getPet());
        newVisit.setVeterinarian(visit.getVeterinarian());

        return visitRepository.save(newVisit);


    }

    @Transactional
    public void addNewMedicineToVisit(VisitMedicine visitMedicine) {
        Visit visit = visitRepository.findById(visitMedicine.getVisit().getId()).get();
            visitsMedicinesRepository.save(visitMedicine);
        }
}

