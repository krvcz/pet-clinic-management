package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.repository.BreedRepository;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;
import pl.ssanko.petclinic.views.customer.CustomerView;

public class CustomerOnlyReadView extends CustomerForm{


    public CustomerOnlyReadView(Grid<Customer> customerGrid, CustomerService customerService, PetService petService, SpeciesService speciesService, Customer customer) {
        super(customerGrid, customerService, petService, speciesService, customer);

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
//
    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
    }

    @Override
    protected void save() {}

    @Override
    protected void delete() {}


}