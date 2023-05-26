package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20, message = "Przekroczono limit znaków 20!")
    @NotBlank(message = "Wartość nie może być pusta!")
    private String name;
    @Column(name = "registration_number")
    @Size(max = 10, message = "Przekroczono limit znaków 10!")
    @NotBlank(message = "Wartość nie może być pusta!")
    private String registrationNumber;
    @Size(max = 100, message = "Przekroczono limit znaków 100!")
    @NotBlank(message = "Wartość nie może być pusta!")
    private String composition;
    @Size(max = 50, message = "Przekroczono limit znaków 50!")
    private String dosage;
    @Size(max = 100, message = "Przekroczono limit znaków 100!")
    private String contraindications;
    @Column(name = "side_effects")
    @Size(max = 100, message = "Przekroczono limit znaków 100!")
    private String sideEffects;
    @Column(name = "administration_route")
    @Size(max = 20, message = "Przekroczono limit znaków 20!")
    private String administrationRoute;
    @Size(max = 20, message = "Przekroczono limit znaków 20!")
    @NotBlank(message = "Wartość nie może być pusta!")
    private String manufacturer;
    @Column(name = "is_active")
    @NotNull
    private boolean active = true;
    @OneToMany(mappedBy = "medicine", fetch = FetchType.EAGER)
    private Set<MedicineUnit> medicineUnits;

    public Medicine attachMedicineUnit(MedicineUnit medicineUnit) {
        if (medicineUnits == null) {
            medicineUnits = new LinkedHashSet<>();
        }
            medicineUnit.setMedicine(this);
            medicineUnits.add(medicineUnit);

        return this;
    }

    public Medicine detachMedicineUnit(MedicineUnit medicineUnit) {
        this.getMedicineUnits().remove(medicineUnit);

        return this;
    }
}
