package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "species")
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "species", fetch = FetchType.EAGER)
    private Set<Breed> breeds;

    @Override
    public String toString() {
        return name;
    }

}
