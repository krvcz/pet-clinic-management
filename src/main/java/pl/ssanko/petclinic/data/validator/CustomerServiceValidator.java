package pl.ssanko.petclinic.data.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.CustomerRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceValidator {

    private final CustomerRepository customerRepository;

    private final Validator validator;

    public void validate(Customer customer) throws NotUniqueException {

        Set<ConstraintViolation<Customer>> validate = validator.validate(customer);

        StringBuilder stringBuilder = new StringBuilder();

        for (ConstraintViolation<Customer> violation: validate) {
            stringBuilder.append(violation.getMessage()).append("\n");
        }

        if (customerRepository.isNotUnique(customer.getFirstName(), customer.getLastName(), customer.getEmail())) {
            stringBuilder.append("Podobny klient już istnieje!");
        }

        if (!stringBuilder.isEmpty()) {
            throw new NotUniqueException(stringBuilder.toString());
        }

    }
}
