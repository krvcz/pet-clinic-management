package pl.ssanko.petclinic.data.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.CustomerRepository;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerServiceValidatorTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Validator validator;

    private CustomerServiceValidator customerServiceValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerServiceValidator = new CustomerServiceValidator(customerRepository, validator);
    }

    @Test
    void validateWithValidCustomerNoExceptionThrown() throws NotUniqueException {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");

        Set<ConstraintViolation<Customer>> validate = new HashSet<>();

        when(validator.validate(customer)).thenReturn(validate);
        when(customerRepository.isNotUnique(customer.getFirstName(), customer.getLastName(), customer.getEmail()))
                .thenReturn(false);

        assertDoesNotThrow(() -> customerServiceValidator.validate(customer));

        Mockito.verify(validator).validate(customer);
        Mockito.verify(customerRepository).isNotUnique(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    @Test
    void validateWithInvalidCustomerThrowsNotUniqueException() {
        Customer customer = new Customer();
        customer.setFirstName("");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");

        Set<ConstraintViolation<Customer>> validate = new HashSet<>();
        validate.add(mock(ConstraintViolation.class));

        when(validator.validate(customer)).thenReturn(validate);
        when(customerRepository.isNotUnique(customer.getFirstName(), customer.getLastName(), customer.getEmail()))
                .thenReturn(false);

        assertThrows(NotUniqueException.class, () -> customerServiceValidator.validate(customer));

        Mockito.verify(validator).validate(customer);

    }

    @Test
    void validateWithExistingCustomerThrowsNotUniqueException() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");

        Set<ConstraintViolation<Customer>> validate = new HashSet<>();

        when(validator.validate(customer)).thenReturn(validate);
        when(customerRepository.isNotUnique(customer.getFirstName(), customer.getLastName(), customer.getEmail()))
                .thenReturn(true);

        assertThrows(NotUniqueException.class, () -> customerServiceValidator.validate(customer));

        Mockito.verify(validator).validate(customer);
        Mockito.verify(customerRepository).isNotUnique(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }
}