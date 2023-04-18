package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String message;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer client;
    // konstruktory, gettery i settery
}
