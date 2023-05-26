package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Wartość nie może być pusta!")
    private String name;

    @NotBlank(message = "Wartość nie może być pusta!")
    private String gender;

    @Past(message = "Data musi być z przeszłości!")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;
    @Column(name = "is_active")
    @NotNull
    private boolean active = true;

    @Override
    public String toString() {
        return name  + " " +
                "(" + customer +
                ")";
    }
}
