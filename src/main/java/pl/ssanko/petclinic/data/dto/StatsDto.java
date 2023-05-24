package pl.ssanko.petclinic.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatsDto {

    private Long numberOfVisitOnPaymentStatus;
    private Long numberOfProcessingVisits;
    private Long numberOfVisitInLast24Hours;
    private Long numberOfEventToday;
    private Long numberOfMedicines;
    private Long numberOfMedicalProcedures;

    public StatsDto(Long numberOfVisitOnPaymentStatus,
                    Long numberOfProcessingVisits,
                    Long numberOfVisitInLast24Hours,
                    Long numberOfEventToday,
                    Long numberOfMedicines,
                    Long numberOfMedicalProcedures) {

        this.numberOfVisitOnPaymentStatus = numberOfVisitOnPaymentStatus;
        this.numberOfProcessingVisits = numberOfProcessingVisits;
        this.numberOfVisitInLast24Hours = numberOfVisitInLast24Hours;
        this.numberOfEventToday = numberOfEventToday;
        this.numberOfMedicines = numberOfMedicines;
        this.numberOfMedicalProcedures = numberOfMedicalProcedures;

    }

}
