package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "visits")

public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String description;
    private String status;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "visits_medicines",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id"))
    private List<Medicine> medicineList;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "visits_medical_procedures",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_procedure_id"))
    private List<MedicalProcedure> medicalProcedureList;

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                '}';
    }
}
