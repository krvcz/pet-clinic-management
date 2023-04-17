package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String description;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;
    @OneToMany(mappedBy = "visit")
    private List<VisitMedicine> visitMedicines;
    @OneToMany(mappedBy = "visit")
    private List<VisitMedicalProcedure> visitMedicalProcedures;
    // konstruktory, gettery i settery
}
