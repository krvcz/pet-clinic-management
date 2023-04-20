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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.customer.component.CustomerForm;

import java.util.List;

@PageTitle("Customers")
@Route(value = "customers", layout = MainLayout.class)
@PermitAll
public class CustomerView extends VerticalLayout {

    private final CustomerService customerService;

    private Grid<Customer> grid;

    private TextField filterTextField;

    @Autowired
    public CustomerView(CustomerService customerService) {

        this.customerService = customerService;

        // Tworzenie tabeli z klientami
        grid = new Grid<>(Customer.class);
        grid.setColumns("firstName", "lastName", "email", "phoneNumber");

        // Pobranie klientów i ustawienie ich w tabeli

        grid.setItems(query ->
                customerService.getAllCustomers(PageRequest.of(query.getPage(), query.getPageSize())));

        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.addValueChangeListener(e -> updateGrid());
        add(filterTextField, grid);

        // Dodanie przycisków do zarządzania klientami
        Button addButton = new Button("Add new customer", e -> showCustomerForm(new Customer()));
        Button editButton = new Button("Edit customer", e -> {
            Customer selectedCustomer = grid.getSelectedItems().iterator().next();
            showCustomerForm(selectedCustomer);
        });
        Button deleteButton = new Button("Delete customer", e -> {
            Customer selectedCustomer = grid.getSelectedItems().iterator().next();
            customerService.deleteCustomer(selectedCustomer);
            updateGrid();
        });
        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, editButton, deleteButton);
        add(buttonLayout);

    }

    public void updateGrid() {
        String filter = filterTextField.getValue();
        grid.setItems(query ->
                customerService.getCustomersByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter));
    }

    private void showCustomerForm(Customer customer) {
        CustomerForm customerForm = new CustomerForm(this, customerService);
        customerForm.setCustomer(customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }
}
