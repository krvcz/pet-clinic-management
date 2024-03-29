package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.dto.PetStatsDto;
import pl.ssanko.petclinic.data.dto.StatsDto;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.repository.*;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class StatsService {

    private final EventRepository eventRepository;
    private final VisitRepository visitRepository;
    private final MedicalProcedureRepository medicalProcedureRepository;
    private final MedicineRepository medicineRepository;
    private final PetRepository petRepository;

    private final CustomerRepository customerRepository;

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

    public CustomerStatsDto getCustomerStats(Long customerId) {
            Long numberOfPets = petRepository.findAllByCustomerIdAndActiveTrue(Pageable.unpaged(), customerId).stream().count();
            Long numberOfVisits = visitRepository.findAllByCustomerId(Pageable.unpaged(), customerId).stream().count();

            return new CustomerStatsDto(numberOfVisits, numberOfPets);
    }

    public PetStatsDto getPetStats(Long petId) {
        return petRepository.getPetStats(petId);
    }
}
