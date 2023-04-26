package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.CustomerRepository;
import pl.ssanko.petclinic.data.repository.PetRepository;
import pl.ssanko.petclinic.data.validator.CustomerServiceValidator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PetRepository petRepository;

    private final CustomerServiceValidator customerServiceValidator;

    @Transactional(readOnly = true)
    public Stream<Customer> getAllCustomers(Pageable pageable) {

        return customerRepository.findAll(pageable).stream();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long customerId) {
        return customerRepository.getById(customerId);
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
        petRepository.saveAll(customer.getPets());
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

//        customerRepository.save(customer);

//        if (customer.getPets() != null) {
//            petRepository.saveAll(customer.getPets());
//        }


        Set<Pet> listToRemove = customerOld.getPets().stream()
                        .filter((pet) -> !customer.getPets().contains(pet))
                        .collect(Collectors.toSet());

        petRepository.deleteAll(listToRemove);

        customerOld.setPets(customer.getPets());

        petRepository.saveAll(customerOld.getPets());

        return customerOld;

    }
}
