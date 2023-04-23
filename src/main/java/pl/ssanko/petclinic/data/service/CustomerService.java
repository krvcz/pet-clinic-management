package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.CustomerRepository;
import pl.ssanko.petclinic.data.validator.CustomerServiceValidator;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerServiceValidator customerServiceValidator;

    @Transactional(readOnly = true)
    public Stream<Customer> getAllCustomers(Pageable pageable) {

        return customerRepository.findAll(pageable).stream();
    }

    @Transactional
    public void deleteCustomer(Customer selectedCustomer) {

        customerRepository.delete(selectedCustomer);
    }

    @Transactional(readOnly = true)
    public Stream<Customer> getCustomersByFilter(Pageable pageable, String filter) {

        return customerRepository.findAllByFilter(pageable, filter).stream();
    }

    @Transactional
    public Customer addCustomer(Customer customer) throws NotUniqueException {
        customerServiceValidator.validate(customer);
        customerRepository.save(customer);

       return customer;

    }

    @Transactional
    public Customer editCustomer(Long id, Customer customer) {
        Customer customerOld = customerRepository.getReferenceById(id);
        customerOld.setFirstName(customer.getFirstName());
        customerOld.setLastName(customer.getLastName());
        customerOld.setEmail(customer.getEmail());
        customerOld.setPhoneNumber(customer.getPhoneNumber());

        customerRepository.save(customer);

        return customer;

    }
}