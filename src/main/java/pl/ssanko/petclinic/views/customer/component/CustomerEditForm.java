package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.exception.NotUniqueException;
import pl.ssanko.petclinic.data.repository.BreedRepository;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;
import pl.ssanko.petclinic.views.customer.CustomerView;

public class CustomerEditForm extends CustomerForm{


    public CustomerEditForm(Grid<Customer> customerGrid, CustomerService customerService, PetService petService, SpeciesService speciesService, Customer customer) {
        super(customerGrid, customerService, petService, speciesService, customer);
        petGrid.setItems(query -> petService.getPetsByCustomer(PageRequest.of(query.getPage(), query.getPageSize()), customer.getId()));


    }

    @Override
    protected void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
    }

    @Override
    protected void save() {

            customerService.editCustomer(customer.getId(), customer);
            Dialog dialog = (Dialog) getParent().get();
            dialog.close();
            Notification.show("Zaktualizowano klienta!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        refreshGrid();
    }

}

