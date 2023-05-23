package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "medicine_units")
public class MedicineUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Wartość nie może być pusta!")
    @PositiveOrZero(message = "Wartość musi być dodania lub równa 0!")
    private BigDecimal price;
    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 50, message = "Przekroczono limit znaków 50!")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

}
