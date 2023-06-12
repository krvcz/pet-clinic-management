package pl.ssanko.petclinic.data.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.repository.CustomerRepository;
import pl.ssanko.petclinic.data.repository.PetRepository;
import pl.ssanko.petclinic.data.validator.CustomerServiceValidator;
import pl.ssanko.petclinic.security.SecurityConfigurationTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Import(SecurityConfigurationTest.class)
@Transactional
@Rollback
class CustomerServiceIT {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerServiceValidator customerServiceValidator;
    private Pet pet;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        petRepository.deleteAll();
        customer = new Customer();
        customer.setFirstName("Użytkownik");
        customer.setLastName("Systemu");
        customer.setEmail("mail@mail.com");
        customer.setPhoneNumber("12345");
        customerRepository.save(customer);
    }


    @Test
    void shouldReturnOneCustomer() {

        // When
        List<Customer> customersList = customerService.getAllCustomers(Pageable.unpaged()).collect(Collectors.toList());

        // Then
        assertThat(customersList).hasSize(1)
                .extracting(Customer::getFirstName)
                .containsExactlyInAnyOrder("Użytkownik");

    }

    @Test
    void shouldReturnPetList() {
        // Given
        Pet pet1 = new Pet();
        pet1.setName("Pet1");
        pet1.setGender("M");
        Pet pet2 = new Pet();
        pet2.setName("Pet2");
        pet2.setGender("M");
        Pet pet3 = new Pet();
        pet3.setName("Pet3");
        pet3.setGender("M");

        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);

        customer.attachPet(pet1);
        customer.attachPet(pet2);
        customer.attachPet(pet3);

        // When
        Customer customer = customerService.getAllCustomers(Pageable.unpaged()).collect(Collectors.toList()).get(0);

        // Then
        assertThat(customer.getPets()).hasSize(3)
                .extracting(Pet::getName)
                .containsExactlyInAnyOrder("Pet1", "Pet2", "Pet3");

    }

}