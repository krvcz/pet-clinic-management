package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medical_procedures")
public class MedicalProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private float cost;

}
