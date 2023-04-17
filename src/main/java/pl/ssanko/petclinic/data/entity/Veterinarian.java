package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;

@Entity
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    @OneToMany(mappedBy = "veterinarian")
    private List<Visit> visits;
    @OneToMany(mappedBy = "veterinarian")
    private List<Availability> availabilities;
    // konstruktory, gettery i settery
}
