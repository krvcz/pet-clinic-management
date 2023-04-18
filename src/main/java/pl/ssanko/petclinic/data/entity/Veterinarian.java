package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "veterinarians")
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
}
