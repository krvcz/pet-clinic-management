package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;

public class CustomerDeleteForm extends CustomerForm {


    public CustomerDeleteForm(Grid<Customer> customerGrid, CustomerService customerService, PetService petService, SpeciesService speciesService, Customer customer) {
        super(customerGrid, customerService, petService, speciesService, customer);

        firstName.setReadOnly(true);
        lastName.setReadOnly(true);
        email.setReadOnly(true);
        phoneNumber.setReadOnly(true);
        plusButton.setEnabled(false);
        editButton.setEnabled(false);
        removeButton.setEnabled(false);
        petGrid.setItems(query -> petService.getPetsByCustomer(PageRequest.of(query.getPage(), query.getPageSize()), customer.getId()));
        petGrid.setEnabled(false);
        save.setText("Usuń");
        save.setIcon(new Icon(VaadinIcon.ERASER));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        binder.bindInstanceFields(customer);
        binder.setValidatorsDisabled(true);
//
    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
    }

    @Override
    protected void save() {
        customerService.deleteCustomer(customer);
        Notification.show("Usunięto klienta!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        refreshGrid();

        Dialog dialog = (Dialog) getParent().get();
        dialog.close();


    }



}