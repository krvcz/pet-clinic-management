package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;
}
