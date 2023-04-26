package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.repository.BreedRepository;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;
import pl.ssanko.petclinic.views.customer.CustomerView;

public class CustomerOnlyReadView extends CustomerForm{


    public CustomerOnlyReadView(CustomerView customersView, CustomerService customerService, PetService petService, SpeciesService speciesService, Customer customer) {
        super(customersView, customerService, petService, speciesService, customer);

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        phoneNumber.setEnabled(false);
        save.setEnabled(false);
        delete.setEnabled(false);
        plusButton.setEnabled(false);
        editButton.setEnabled(false);
        removeButton.setEnabled(false);
        petGrid.setItems(query -> petService.getPetsByCustomer(PageRequest.of(query.getPage(), query.getPageSize()), customer.getId()));
        binder.bindInstanceFields(customer);
//        petGrid.setItems(customer.getPets());
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