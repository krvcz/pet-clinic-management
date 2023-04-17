package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
