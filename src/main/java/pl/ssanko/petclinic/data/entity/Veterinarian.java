package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "veterinarians")
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    @Column(name = "is_active")
    private boolean active = true;

    @Override
    public String toString() {
        return   firstName + " " +
                 lastName + " " +
                "(" + specialization  +
                ")";
    }
}
