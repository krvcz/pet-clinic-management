package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.views.customer.CustomerView;

public class CustomerAddForm extends CustomerForm{


    public CustomerAddForm(CustomerView customersView, CustomerService customerService, Customer customer) {
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
        try {

            customerService.addCustomer(customer);
            Dialog dialog = (Dialog) getParent().get();
            dialog.close();
            Notification.show("Operacja się powiodła!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        } catch (NotUniqueException ex) {
            Dialog dialog = new Dialog();
            Button okButton = new Button("Rozumiem");
            okButton.addClickListener(e -> dialog.close());
            dialog.add(ex.getMessage());
            dialog.add(okButton);
            add(dialog);
            dialog.open();

        }

        customersView.updateGrid();
    }

    @Override
    protected void delete() {
        customerService.deleteCustomer(customer);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        customersView.updateGrid();
    }
}
