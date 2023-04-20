package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import jakarta.persistence.Column;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.views.customer.CustomerView;

import java.time.LocalDate;

public class CustomerForm extends FormLayout {

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField email = new TextField("Email");
    private TextField phoneNumber = new TextField("Phone");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    private Grid<Pet> petGrid = new Grid<>(Pet.class);
    private Button addPetButton = new Button("Add pet");

    private Tabs tabs = new Tabs();

    private Tab petTab = new Tab();

    private Binder<Customer> binder = new Binder<>(Customer.class);

    private CustomerService customerService;

    private Customer customer;
    private CustomerView customersView;

    public CustomerForm(CustomerView customersView, CustomerService customerService) {
        this.customersView = customersView;
        this.customerService = customerService;


        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        petGrid.setColumns("name", "species", "breed", "gender", "dateOfBirth");
        petTab.add(petGrid);
        tabs.add(petTab);


       setColspan(petGrid, 2);


        add(firstName, lastName, email, phoneNumber, petGrid, createButtonsLayout());


        cancel.addClickListener(e -> cancel());
        save.addClickListener(e -> {
            save();
            Notification.show("Changed!");
        });
        delete.addClickListener(e -> delete());

    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);

        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
           firstName.focus();
        }
    }

    private void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        customersView.updateGrid();
    }

    private void save() {
        customerService.addCustomer(customer);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();

        customersView.updateGrid();
    }

    private void delete() {
        customerService.deleteCustomer(customer);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        customersView.updateGrid();
    }

    private HorizontalLayout createButtonsLayout() {
        return new HorizontalLayout(save, delete, cancel);
    }
}