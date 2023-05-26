package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.MedicalProcedureRepository;
import pl.ssanko.petclinic.data.repository.MedicineRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicalProceduresRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicinesRepository;
import pl.ssanko.petclinic.data.validator.MedicalProcedureServiceValidator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MedicalProcedureService {
    private final MedicalProcedureRepository medicalProcedureRepository;

    private final VisitsMedicalProceduresRepository visitsMedicalProceduresRepository;

    private final MedicalProcedureServiceValidator medicalProcedureServiceValidator;

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getMedicalProcedures(Pageable pageable){
        return medicalProcedureRepository.findAllByActiveTrue(pageable).stream();
    }


    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getBasicMedicalProcedures(Pageable pageable) {
        return medicalProcedureRepository.findAllByTypeEqualsAndActiveTrue("Badanie laboratoryjne", pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getBasicMedicalProceduresAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicalProceduresRepository.findVisitMedicalProceduresByVisitId(visitId, pageable)
                .stream()
                .map(VisitMedicalProcedure::getMedicalProcedure)
                .filter(e -> e.getType().equals("Badanie laboratoryjne"));
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSpecialMedicalProcedures(Pageable pageable) {
        return medicalProcedureRepository.findAllByTypeEqualsAndActiveTrue("RTG/USG", pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSpecialMedicalProceduresAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicalProceduresRepository.findVisitMedicalProceduresByVisitId(visitId, pageable)
                .stream()
                .map(VisitMedicalProcedure::getMedicalProcedure)
                .filter(e -> e.getType().equals("RTG/USG"));
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSurgeryMedicalProcedures(Pageable pageable) {
        return medicalProcedureRepository.findAllByTypeEqualsAndActiveTrue("Zabieg", pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSurgeryMedicalProceduresAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicalProceduresRepository.findVisitMedicalProceduresByVisitId(visitId, pageable)
                .stream()
                .map(VisitMedicalProcedure::getMedicalProcedure)
                .filter(e -> e.getType().equals("Zabieg"));
    }


    @Transactional
    public MedicalProcedure saveMedicalProcedure(MedicalProcedure medicalProcedure) throws NotUniqueException {
        Optional<MedicalProcedure> persistedMedicalProcedureOptional = medicalProcedure.getId() == null ?
                Optional.empty() :  medicalProcedureRepository.findById(medicalProcedure.getId());

        if (persistedMedicalProcedureOptional.isPresent()) {
            MedicalProcedure persistedMedicalProcedure = persistedMedicalProcedureOptional.get();
            persistedMedicalProcedure.setDescription(medicalProcedure.getDescription());
            persistedMedicalProcedure.setName(medicalProcedure.getName());
            persistedMedicalProcedure.setType(medicalProcedure.getType());
            persistedMedicalProcedure.setPrice(medicalProcedure.getPrice());
            return medicalProcedureRepository.save(persistedMedicalProcedure);
        }


        MedicalProcedure newMedicalprocedure = new MedicalProcedure();
        newMedicalprocedure.setDescription(medicalProcedure.getDescription());
        newMedicalprocedure.setName(medicalProcedure.getName());
        newMedicalprocedure.setType(medicalProcedure.getType());
        newMedicalprocedure.setPrice(medicalProcedure.getPrice());

        medicalProcedureServiceValidator.validate(newMedicalprocedure);

        return medicalProcedureRepository.save(newMedicalprocedure);
    }

    @Transactional
    public void deleteMedicalProcedure(MedicalProcedure medicalProcedure) {
        medicalProcedure.setActive(false);
        medicalProcedureRepository.save(medicalProcedure);

    }

    public Stream<MedicalProcedure> getMedicalProceduresByFilter(PageRequest pageable, String filter) {
            return medicalProcedureRepository.findAllByFilter(pageable, filter).stream();

    }
}

