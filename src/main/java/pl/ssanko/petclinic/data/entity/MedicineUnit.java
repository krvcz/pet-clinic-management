package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "medicine_units")
public class MedicineUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
}
