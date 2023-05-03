package pl.ssanko.petclinic.views.customer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.repository.BreedRepository;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.customer.component.CustomerAddForm;
import pl.ssanko.petclinic.views.customer.component.CustomerEditForm;
import pl.ssanko.petclinic.views.customer.component.CustomerForm;
import pl.ssanko.petclinic.views.customer.component.CustomerOnlyReadView;

import java.util.List;
import java.util.Optional;

@PageTitle("Customers")
@Route(value = "customers", layout = MainLayout.class)
@PermitAll
public class CustomerView extends VerticalLayout {

    private final CustomerService customerService;

    private final PetService petService;

    private final SpeciesService speciesService;

    private Grid<Customer> grid;

    private TextField filterTextField;

    @Autowired
    public CustomerView(CustomerService customerService, SpeciesService speciesService, PetService petService) {

        this.customerService = customerService;
        this.speciesService = speciesService;
        this.petService = petService;


        // Tworzenie tabeli z klientami
        grid = new Grid<>(Customer.class);
        grid.setColumns("firstName", "lastName", "email", "phoneNumber");
        grid.getColumnByKey("firstName").setHeader("Imię");
        grid.getColumnByKey("lastName").setHeader("Adres Email");
        grid.getColumnByKey("phoneNumber").setHeader("Numer telefonu");

        // Pobranie klientów i ustawienie ich w tabeli

        grid.setItems(query ->
                customerService.getAllCustomers(PageRequest.of(query.getPage(), query.getPageSize())));

        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.addValueChangeListener(e -> updateGrid());
        add(filterTextField, grid);

        // Dodanie przycisków do zarządzania klientami
        Button addButton = new Button("Dodaj nowego klienta", e -> showAddCustomerForm(new Customer()));
        Button editButton = new Button("Edytuj klienta", e -> {
            if (grid.getSelectedItems().iterator().hasNext()) {
                Customer selectedCustomer = grid.getSelectedItems().iterator().next();
                showEditCustomerForm(selectedCustomer);
            }
        });

        Button deleteButton = new Button("Usuń klienta", e -> {
            Customer selectedCustomer = grid.getSelectedItems().iterator().next();
            customerService.deleteCustomer(selectedCustomer);
            updateGrid();
        });

        grid.addItemClickListener(listener ->
                {
                    if (listener.getClickCount() == 2) {

                        // TODO przy wprowadzaniu DTO, spróbować to sfrosować
                       Customer cus = customerService.getCustomerById(listener.getItem().getId());
                       Customer cus1 = listener.getItem();
//
//                       showReadOnlyCustomerForm(cus);
                        showReadOnlyCustomerForm(listener.getItem());
                    }

                }
        );
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, editButton, deleteButton);
        add(buttonLayout);



    }

    public void updateGrid() {
        String filter = filterTextField.getValue();
        grid.setItems(query ->
                customerService.getCustomersByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter));
    }

    private void showAddCustomerForm(Customer customer) {
        CustomerForm customerForm = new CustomerAddForm(this, customerService, petService, speciesService, customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }

    private void showEditCustomerForm(Customer customer) {
//        customer.addPet(new Pet());
        CustomerForm customerForm = new CustomerEditForm(this, customerService, petService, speciesService, customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }

    @Transactional
    public void showReadOnlyCustomerForm(Customer customer) {
        CustomerForm customerForm = new CustomerOnlyReadView(this, customerService, petService,  speciesService, customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }

}
