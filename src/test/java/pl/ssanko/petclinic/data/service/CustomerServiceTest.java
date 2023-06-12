package pl.ssanko.petclinic.data.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.CustomerRepository;
import pl.ssanko.petclinic.data.repository.PetRepository;
import pl.ssanko.petclinic.data.validator.CustomerServiceValidator;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private CustomerServiceValidator customerServiceValidator;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        Pageable pageable = Mockito.mock(Pageable.class);
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        Mockito.when(customerRepository.findAllByActiveTrue(pageable)).thenReturn(new PageImpl<>(customers));

        Stream<Customer> result = customerService.getAllCustomers(pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(customers.size(), result.count());
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(customerId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(customerId, result.getId());
    }

    @Test
    public void testDeleteCustomer() {
        Customer selectedCustomer = new Customer();

        customerService.deleteCustomer(selectedCustomer);

        Mockito.verify(customerRepository, Mockito.times(1)).save(selectedCustomer);
        Assertions.assertFalse(selectedCustomer.isActive());
    }

    @Test
    public void testGetCustomersByFilter() {
        Pageable pageable = Mockito.mock(Pageable.class);
        String filter = "John";

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        Mockito.when(customerRepository.findAllByFilter(pageable, filter)).thenReturn(new PageImpl<>(customers));

        Stream<Customer> result = customerService.getCustomersByFilter(pageable, filter);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(customers.size(), result.count());
    }

    @Test
    public void testAddCustomer() throws NotUniqueException {
        Customer customer = new Customer();
        customer.setPets(new HashSet<>());

        customerService.addCustomer(customer);

        Mockito.verify(customerServiceValidator, Mockito.times(1)).validate(customer);
        Mockito.verify(petRepository, Mockito.times(1)).saveAll(customer.getPets());
        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
    }

    @Test
    public void testEditCustomer() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("1234567890");
        customer.setPets(new HashSet<>(Set.of(new Pet())));

        Customer customerOld = new Customer();
        customerOld.setId(id);
        customerOld.setPets(new HashSet<>(Set.of(new Pet())));

        Mockito.when(customerRepository.getReferenceById(Mockito.anyLong())).thenReturn(customerOld);
        Mockito.when(petRepository.saveAll(Mockito.anySet())).thenReturn(null);

        Customer result = customerService.editCustomer(id, customer);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(customer.getFirstName(), result.getFirstName());
        Assertions.assertEquals(customer.getLastName(), result.getLastName());
        Assertions.assertEquals(customer.getEmail(), result.getEmail());
        Assertions.assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());

        Mockito.verify(petRepository, Mockito.times(1)).saveAll(Mockito.anySet());
    }
}