package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pl.ssanko.petclinic.data.dto.VisitDto;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.repository.VisitsMedicalProceduresRepository;
import pl.ssanko.petclinic.data.repository.VisitsMedicinesRepository;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitMapper {
    
    private final VisitsMedicinesRepository visitsMedicinesRepository;
    
    private final VisitsMedicalProceduresRepository visitsMedicalProceduresRepository;

    public VisitDto map(Visit visit) {
        VisitDto visitDto = new VisitDto();
        String medicines = visitsMedicinesRepository.findVisitMedicineByVisitId(visit.getId(), Pageable.unpaged())
                .stream()
                .map(row -> row.getMedicine().getName() +
                        "( " + row.getMedicine().getRegistrationNumber() + ") " +
                        " - " + row.getMedicineUnit().getUnit() + " x " + row.getQuantity())
                .collect(Collectors.joining(" ; "));

        String medicalProcedures = visitsMedicalProceduresRepository.findVisitMedicalProceduresByVisitId(visit.getId(), Pageable.unpaged()).stream()
                .map(row -> row.getMedicalProcedure().getName())
                .collect(Collectors.joining(" ; "));


        visitDto.setId(visit.getId());
        visitDto.setDescription(visit.getDescription());
        visitDto.setDate(visit.getDate().format((DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));
        visitDto.setStatus(visit.getStatus());
        visitDto.setPetId(visit.getPet().getId());
        visitDto.setVeterinarianId(visit.getVeterinarian().getId());
        visitDto.setMedicines(medicines);
        visitDto.setMedicalProcedures(medicalProcedures);

        if (visit.getVisitDetail() != null) {

            if (visit.getVisitDetail().getWeight() != null & visit.getVisitDetail().getTemperature() != null) {
                String basicInfo = "Waga :" + visit.getVisitDetail().getWeight() + " Temperatura : " + visit.getVisitDetail().getTemperature();
                visitDto.setBasicInfo(basicInfo);
            } else {
                visitDto.setBasicInfo("");
            }
            visitDto.setDiagnosis(visit.getVisitDetail().getDiagnosis());
            visitDto.setInterview(visit.getVisitDetail().getInterview());
            visitDto.setRecommendations(visit.getVisitDetail().getRecommendations());
            visitDto.setClinicalTrails(visit.getVisitDetail().getClinicalTrials());
            visitDto.setComment(visit.getVisitDetail().getComment());
        }
        else {
                visitDto.setBasicInfo("");
                visitDto.setDiagnosis("");
                visitDto.setInterview("");
                visitDto.setRecommendations("");
                visitDto.setClinicalTrails("");
                visitDto.setComment("");

            }


        return visitDto;

    }
}
