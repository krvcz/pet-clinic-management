package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "visits_medicines")
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
    @NotNull(message = "Wartość nie może być pusta!")
    private Double quantity;
    @ManyToOne
    @JoinColumn(name = "medicine_unit_id")
    @NotNull(message = "Wartość nie może być pusta!")
    private MedicineUnit medicineUnit;

    public VisitMedicine(Visit visit, Medicine medicine, MedicineUnit medicineUnit, Double quantity) {
        this.visit = visit;
        this.medicine = medicine;
        this.medicineUnit = medicineUnit;
        this.quantity = quantity;
    }

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
