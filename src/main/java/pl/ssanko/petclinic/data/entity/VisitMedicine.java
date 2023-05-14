package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "visits_medicines1")
@IdClass(VisitMedicine.VisitMedicinePK.class)
public class VisitMedicine {
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    @Id
    private Medicine medicine;
    @ManyToOne
    @JoinColumn(name = "visit_id")
    @Id
    private Visit visit;
    private String dosage;

    public VisitMedicine(Visit visit, Medicine medicine) {
        this.visit = visit;
        this.medicine = medicine;
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    public static class VisitMedicinePK implements Serializable {
        private Visit visit;
        private Medicine medicine;

    }

}
