package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    @NotBlank(message = "Wartość nie może być pusta!")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Wartość nie może być pusta!")
    private String lastName;
    @Column(name = "phone_number")
    @NotBlank(message = "Wartość nie może być pusta!")
    @Pattern(regexp=  "^[0-9-+\\s]+$", message = "Niepoprawny numer telefonu!")
    private String phoneNumber;
    @NotBlank(message = "Wartość nie może być pusta!")
    @Email(message = "Niepoprawny adres email!")
    private String email;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Pet> pets;

    public Customer addPet(Pet pet) {
        if (pets == null) {
            pets = new LinkedHashSet<>();
        }

        pet.setCustomer(this);
        pets.add(pet);



        return this;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + 0 +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
