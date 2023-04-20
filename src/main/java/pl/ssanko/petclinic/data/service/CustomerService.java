package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.repository.CustomerRepository;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

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
    public Customer addCustomer(Customer customer) {
        // TODO Walidacja, czy istnieje !
        return customerRepository.save(customer);

    }
}
