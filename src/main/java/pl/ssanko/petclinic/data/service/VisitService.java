package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.dto.ProductDto;
import pl.ssanko.petclinic.data.dto.VisitDto;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.repository.VisitRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicalProceduresRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicinesRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;

    private final VisitsMedicinesRepository visitsMedicinesRepository;

    private final VisitsMedicalProceduresRepository visitsMedicalProceduresRepository;

    private final VisitMapper visitMapper;

    @Transactional
    public void removeVisitMedicine(Long visitId) {
        visitsMedicinesRepository.deleteAllByVisitId(visitId);

    }

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
            visitsMedicinesRepository.save(visitMedicine);

        }

    @Transactional
    public void removeVisitMedicalProcedure(Long visitId) {
        visitsMedicalProceduresRepository.deleteAllByVisitId(visitId);
    }

    @Transactional
    public void addNewMedicalProcedureToVisit(VisitMedicalProcedure visitMedicalProcedure) {
        visitsMedicalProceduresRepository.save(visitMedicalProcedure);

    }

    @Transactional
    public Visit addNewVisitDetails(Long visitId, VisitDetail visitDetail) {
        Visit visit = visitRepository.findById(visitId).get();
        visit.setVisitDetail(visitDetail);

        return visitRepository.save(visit);

    }

    @Transactional
    public Visit saveVisit(Long visitId, Visit visit) {
        Visit persistedVisit = visitRepository.findById(visitId).get();
        persistedVisit.setVisitDetail(visit.getVisitDetail());
        persistedVisit.setPet(visit.getPet());
        persistedVisit.setVeterinarian(visit.getVeterinarian());
        persistedVisit.setStatus(visit.getStatus());

        return visitRepository.save(visit);

    }


    @Transactional
    public void removeSpecialVisitMedicalProcedure(Long visitId) {
        visitsMedicalProceduresRepository.deleteAllByVisitIdAndSpecifyType(visitId, "RTG/USG");
    }

    @Transactional
    public void removeBasicVisitMedicalProcedure(Long visitId) {
        visitsMedicalProceduresRepository.deleteAllByVisitIdAndSpecifyType(visitId, "Badanie laboratoryjne");
    }

    @Transactional
    public void removeSurgeryVisitMedicalProcedure(Long visitId) {
        visitsMedicalProceduresRepository.deleteAllByVisitIdAndSpecifyType(visitId, "Zabieg");
    }

    @Transactional(readOnly = true)
    public Stream<ProductDto> getProducts(Long visitId, Pageable pageable){
        ProductMapper productMapper = new ProductMapper();
        Stream<ProductDto> visitMedicineStream = visitsMedicinesRepository.findVisitMedicineByVisitId(visitId, pageable)
                .stream()
                .map(productMapper::map);

        Stream<ProductDto> visitMedicalProceduresStream = visitsMedicalProceduresRepository.findVisitMedicalProceduresByVisitId(visitId, pageable)
                .stream()
                .map(productMapper::map);

        return Stream.concat(visitMedicineStream, visitMedicalProceduresStream);
    }

    public void closeVisit(Visit visit) {
        Visit newVisit = new Visit();
        newVisit.setVisitDetail(visit.getVisitDetail() != null ? visit.getVisitDetail() : new VisitDetail());
        newVisit.setId(visit.getId());
        newVisit.setDate(visit.getDate());
        newVisit.setPet(visit.getPet());
        newVisit.setVeterinarian(visit.getVeterinarian());
        newVisit.setDescription(visit.getDescription());
        newVisit.setStatus("Zakończona");

        visitRepository.save(newVisit);
    }

    public Stream<VisitDto> getEntireInfoAboutVisitForCustomer(Long customerId, Pageable pageable) {

        return visitRepository.findAllByCustomerId(pageable, customerId)
                .stream()
                .map(visitMapper::map);
    }
}

