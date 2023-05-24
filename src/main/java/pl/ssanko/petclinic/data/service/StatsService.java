package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ssanko.petclinic.data.dto.StatsDto;
import pl.ssanko.petclinic.data.repository.EventRepository;
import pl.ssanko.petclinic.data.repository.MedicalProcedureRepository;
import pl.ssanko.petclinic.data.repository.MedicineRepository;
import pl.ssanko.petclinic.data.repository.VisitRepository;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class StatsService {

    private final EventRepository eventRepository;
    private final VisitRepository visitRepository;
    private final MedicalProcedureRepository medicalProcedureRepository;
    private final MedicineRepository medicineRepository;

    public StatsDto getSystemStats() {
        Long numberOfEventToday = eventRepository.countToday(LocalDate.now());
        Long numberOfMedicines = medicineRepository.count();
        Long numberOfMedicalProcedures = medicalProcedureRepository.count();
        Long numberOfVisitInLast24Hours = visitRepository.countLast24Hours();
        Long numberOfProcessingVisits = visitRepository.countByStatus("W trakcie");
        Long numberOfVisitOnPaymentStatus = visitRepository.countByStatus("Rozliczenie");

        return new StatsDto(numberOfVisitOnPaymentStatus, numberOfProcessingVisits, numberOfVisitInLast24Hours,
                numberOfEventToday, numberOfMedicines, numberOfMedicalProcedures);

    }




}
