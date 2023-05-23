package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "medical_procedures")
public class MedicalProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 50, message = "Przekroczono limit znaków 50!")
    private String name;
    @Size(max = 100, message = "Przekroczono limit znaków 100!")
    private String description;
    @NotNull(message = "Wartość nie może być pusta!")
    @PositiveOrZero(message = "Wartość musi być dodania lub równa 0!")
    private BigDecimal price;

    @NotBlank(message = "Wartość nie może być pusta!")
    @Size(max = 20, message = "Przekroczono limit znaków 20!")
    private String type;

}
