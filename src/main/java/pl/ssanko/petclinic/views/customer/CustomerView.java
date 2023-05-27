package pl.ssanko.petclinic.views.customer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import pl.ssanko.petclinic.views.customer.component.CustomerDeleteForm;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@PageTitle("Klienci")
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
        grid.setColumns("id", "firstName", "lastName", "email", "phoneNumber");
        grid.getColumnByKey("firstName").setHeader("Imię");
        grid.getColumnByKey("lastName").setHeader("Nazwisko");
        grid.getColumnByKey("email").setHeader("Adres Email");
        grid.getColumnByKey("phoneNumber").setHeader("Numer telefonu");
        grid.getColumnByKey("id").setHeader("Id");
        grid.addComponentColumn( e -> {
            Button customerCard = new Button(new Icon(VaadinIcon.CLIPBOARD_USER));
            customerCard.addThemeVariants(ButtonVariant.LUMO_LARGE);
            customerCard.addClickListener(q -> customerCard.getUI().ifPresent(ui -> ui.navigate(
                    CustomerCardView.class, e.getId())));

            return customerCard;
        }).setHeader("Karta klienta");

        // Pobranie klientów i ustawienie ich w tabeli

        grid.setItems(query ->
                customerService.getAllCustomers(PageRequest.of(query.getPage(), query.getPageSize())));

        grid.setHeight("800px");

        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.addValueChangeListener(e -> updateGrid());


        // Dodanie przycisków do zarządzania klientami
        Button addButton = new Button("Dodaj nowego klienta", new Icon(VaadinIcon.PLUS), e -> showAddCustomerForm(new Customer()));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        Button editButton = new Button("Edytuj klienta", new Icon(VaadinIcon.EDIT), e -> {
            if (grid.getSelectedItems().iterator().hasNext()) {
                Customer selectedCustomer = grid.getSelectedItems().iterator().next();
                showEditCustomerForm(selectedCustomer);
            }
        });

        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button deleteButton = new Button("Usuń klienta", new Icon(VaadinIcon.ERASER), e -> {
            Customer selectedCustomer = grid.getSelectedItems().iterator().next();
            showDeleteCustomerForm(selectedCustomer);
//            updateGrid();
        });

        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);


        HorizontalLayout buttonLayout = new HorizontalLayout(filterTextField, addButton, editButton, deleteButton);
        add(buttonLayout, grid);



    }

    public void updateGrid() {
        String filter = filterTextField.getValue();
        grid.setItems(query ->
                customerService.getCustomersByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter));
    }

    private void showAddCustomerForm(Customer customer) {
        CustomerForm customerForm = new CustomerAddForm(grid, customerService, petService, speciesService, customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }

    private void showEditCustomerForm(Customer customer) {
//        customer.addPet(new Pet());
        CustomerForm customerForm = new CustomerEditForm(grid, customerService, petService, speciesService, customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }


    public void showDeleteCustomerForm(Customer customer) {
        CustomerForm customerForm = new CustomerDeleteForm(grid, customerService, petService,  speciesService, customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }

}
