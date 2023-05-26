package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
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
    private String name;
    @Column(name = "registration_number")
    private String registrationNumber;
    private String composition;
    private String dosage;
    private String contraindications;
    @Column(name = "side_effects")
    private String sideEffects;
    @Column(name = "administration_route")
    private String administrationRoute;
    private String manufacturer;
    @Column(name = "is_active")
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
