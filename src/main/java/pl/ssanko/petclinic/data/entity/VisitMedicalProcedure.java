package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "visits_medical_procedures")
@IdClass(VisitMedicalProcedure.VisitMedicalProcedurePK.class)
public class VisitMedicalProcedure {
    @ManyToOne
    @JoinColumn(name = "medical_procedure_id")
    @Id
    private MedicalProcedure medicalProcedure;
    @ManyToOne
    @JoinColumn(name = "visit_id")
    @Id
    private Visit visit;

    public VisitMedicalProcedure(Visit visit, MedicalProcedure medicalProcedure) {
        this.visit = visit;
        this.medicalProcedure = medicalProcedure;

    }

    @EqualsAndHashCode
    @NoArgsConstructor
    public static class VisitMedicalProcedurePK implements Serializable {
        private Visit visit;
        private MedicalProcedure medicalProcedure;

    }

}
