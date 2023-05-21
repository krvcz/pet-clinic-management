package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.repository.MedicalProcedureRepository;
import pl.ssanko.petclinic.data.repository.MedicineRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicalProceduresRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicinesRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MedicalProcedureService {
    private final MedicalProcedureRepository medicalProcedureRepository;

    private final VisitsMedicalProceduresRepository visitsMedicalProceduresRepository;

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getMedicalProcedures(Pageable pageable){
        return medicalProcedureRepository.findAll(pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getMedicalProceduresAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicalProceduresRepository.findVisitMedicineByVisitId(visitId, pageable)
                .stream()
                .map(VisitMedicalProcedure::getMedicalProcedure);
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getBasicMedicalProcedures(Pageable pageable) {
        return medicalProcedureRepository.findAllByTypeEquals("Badanie laboratoryjne", pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getBasicMedicalProceduresAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicalProceduresRepository.findVisitMedicineByVisitId(visitId, pageable)
                .stream()
                .map(VisitMedicalProcedure::getMedicalProcedure)
                .filter(e -> e.getType().equals("Badanie laboratoryjne"));
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSpecialMedicalProcedures(Pageable pageable) {
        return medicalProcedureRepository.findAllByTypeEquals("RTG/USG", pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSpecialMedicalProceduresAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicalProceduresRepository.findVisitMedicineByVisitId(visitId, pageable)
                .stream()
                .map(VisitMedicalProcedure::getMedicalProcedure)
                .filter(e -> e.getType().equals("RTG/USG"));
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSurgeryMedicalProcedures(Pageable pageable) {
        return medicalProcedureRepository.findAllByTypeEquals("Zabieg", pageable).stream();
    }

    @Transactional(readOnly = true)
    public Stream<MedicalProcedure> getSurgeryMedicalProceduresAssignToVisit(Pageable pageable, Long visitId) {
        return visitsMedicalProceduresRepository.findVisitMedicineByVisitId(visitId, pageable)
                .stream()
                .map(VisitMedicalProcedure::getMedicalProcedure)
                .filter(e -> e.getType().equals("Zabieg"));
    }



}

