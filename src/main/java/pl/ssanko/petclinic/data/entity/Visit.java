package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;
    @ManyToMany
    @JoinTable(
            name = "visits_medicines",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id"))
    private List<Medicine> medicineList;
    @ManyToMany
    @JoinTable(
            name = "visits_medical_procedures",
            joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_procedure_id"))
    private List<MedicalProcedure> medicalProcedureList;
}
