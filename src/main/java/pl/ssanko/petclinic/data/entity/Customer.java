package pl.ssanko.petclinic.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @Column(name = "is_active")
    @NotNull
    private boolean active = true;

    public Customer attachPet(Pet pet) {
        if (pets == null) {
            pets = new LinkedHashSet<>();
        }

        Optional<Pet> existedPet = pets.stream()
                        .filter(x -> x.getId().equals(pet.getId()))
                        .findFirst();


        if (existedPet.isPresent()) {
            existedPet.get().setBreed(pet.getBreed());
            existedPet.get().setSpecies(pet.getSpecies());
            existedPet.get().setName(pet.getName());
            existedPet.get().setGender(pet.getGender());
            existedPet.get().setDateOfBirth(pet.getDateOfBirth());

        } else {
            pet.setCustomer(this);
            pets.add(pet);
        }

        return this;
    }
    public Customer detachPet(Pet pet) {

        pet.setCustomer(null);
        pets.remove(pet);

        return this;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
