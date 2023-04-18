package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private float amount;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
