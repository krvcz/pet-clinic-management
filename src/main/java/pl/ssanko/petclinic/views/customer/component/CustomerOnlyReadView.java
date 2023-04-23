package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.views.customer.CustomerView;

public class CustomerOnlyReadView extends CustomerForm{


    public CustomerOnlyReadView(CustomerView customersView, CustomerService customerService, Customer customer) {
        super(customersView, customerService, customer);

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        phoneNumber.setEnabled(false);
        save.setEnabled(false);
        delete.setEnabled(false);
        plusButton.setEnabled(false);
        editButton.setEnabled(false);
        removeButton.setEnabled(false);
    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        customersView.updateGrid();
    }

    @Override
    protected void save() {}

    @Override
    protected void delete() {}
}