package pl.ssanko.petclinic.data.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


public class CustomerTest {

    private Customer customer;

    private Pet pet;

    private Breed breed;

    private Species species;

    @BeforeEach
    public void setup() {
        customer = new Customer();

        breed = new Breed();
        breed.setName("Labrador");

        species = new Species();
        species.setName("Pies");

        pet = new Pet();
        pet.setId(1L);
        pet.setName("Max");
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setGender("Male");
        pet.setDateOfBirth(LocalDate.of(2018,5,10));
    }

    @Test
    public void testAttachPet() {


        customer.attachPet(pet);

        Set<Pet> pets = customer.getPets();
        Assertions.assertNotNull(pets);
        Assertions.assertEquals(1, pets.size());

        Pet attachedPet = pets.iterator().next();

        Assertions.assertEquals(pet.getId(), attachedPet.getId());
        Assertions.assertEquals(pet.getName(), attachedPet.getName());
        Assertions.assertEquals(pet.getSpecies(), attachedPet.getSpecies());
        Assertions.assertEquals(pet.getBreed(), attachedPet.getBreed());
        Assertions.assertEquals(pet.getGender(), attachedPet.getGender());
        Assertions.assertEquals(pet.getDateOfBirth(), attachedPet.getDateOfBirth());
    }

    @Test
    public void testDetachPet() {
        Set<Pet> pets = new LinkedHashSet<>();
        pets.add(pet);
        customer.setPets(pets);

        customer.detachPet(pet);

        pets = customer.getPets();
        Assertions.assertNotNull(pets);
        Assertions.assertTrue(pets.isEmpty());
    }

    @Test
    public void testToString() {
        customer.setFirstName("John");
        customer.setLastName("Doe");

        String expectedString = "John Doe";
        String actualString = customer.toString();

        Assertions.assertEquals(expectedString, actualString);
    }
}