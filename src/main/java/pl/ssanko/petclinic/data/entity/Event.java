package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Wartość nie może być pusta!")
    @FutureOrPresent(message = "Data musi być z przyszłości!")
    private LocalDateTime date;
    @NotNull(message = "Wartość nie może być pusta!")
    private int duration;
    @NotBlank(message = "Wartość nie może być pusta!")
    private String type;
    @NotBlank(message = "Wartość nie może być pusta!")
    private String description;
}
