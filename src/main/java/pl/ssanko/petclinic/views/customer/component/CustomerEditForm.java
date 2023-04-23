package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.views.customer.CustomerView;

public class CustomerEditForm extends CustomerForm{


    public CustomerEditForm(CustomerView customersView, CustomerService customerService, Customer customer) {
        super(customersView, customerService, customer);
    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        customersView.updateGrid();
    }

    @Override
    protected void save() {

            customerService.editCustomer(customer.getId(), customer);
            Dialog dialog = (Dialog) getParent().get();
            dialog.close();
            Notification.show("Operacja się powiodła!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);


        customersView.updateGrid();
    }

    @Override
    protected void delete() {
        // TODO zrobić okno czy napewno chcesz usunąć
        customerService.deleteCustomer(customer);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        customersView.updateGrid();
    }
}

