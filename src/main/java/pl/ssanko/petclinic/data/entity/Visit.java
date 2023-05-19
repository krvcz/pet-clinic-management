package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    @Embedded
    private VisitDetail visitDetail;

//    @OneToMany(mappedBy = "visit", fetch = FetchType.EAGER)
//    private Set<VisitMedicalProcedure> visitMedicalProcedures;
//
//    @OneToMany(mappedBy = "visit", fetch = FetchType.EAGER)
//    private Set<VisitMedicine> visitMedicines;


    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                '}';
    }
}
