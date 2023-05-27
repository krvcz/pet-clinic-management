package pl.ssanko.petclinic.views.pet.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Breed;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.entity.Species;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;

import java.util.LinkedHashSet;
import java.util.Set;

public class PetForm extends FormLayout {

    private TextField name = new TextField("Imię");

    private ComboBox<Species> species = new ComboBox<>("Gatunek");

    private ComboBox<Breed> breed = new ComboBox<>("Rasa");

    private ComboBox<String> gender = new ComboBox<>("Płeć");

    private DatePicker dateOfBirth = new DatePicker("Data urodzin");

    private Button save = new Button("Zapisz");

    private Button delete = new Button("Usuń");

    private Button cancel = new Button("Anuluj");

    private SpeciesService speciesService;

    private PetService petService;

    private Grid<Pet> petGrid;

    private Pet pet;

    private Customer customer;

    private BeanValidationBinder<Pet> binder = new BeanValidationBinder<>(Pet.class);

    public PetForm(PetService petService, SpeciesService speciesService, Grid<Pet> petGrid, Pet pet, Customer customer) {
        this.speciesService = speciesService;
        this.petService = petService;
        this.petGrid = petGrid;
        this.pet = pet;
        this.customer = customer;

        binder.setBean(pet);

        if (pet == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }


        breed.setEnabled(false);


        species.setItems(
                query -> speciesService.getSpecies(PageRequest.of(query.getPage(), query.getPageSize()))
        );

        species.addValueChangeListener(event -> {
            breed.setEnabled(true);
            breed.setItems(species.getValue().getBreeds());
        });

        gender.setItems(new LinkedHashSet<>(Set.of("M", "K", "Inna")));


        binder.bindInstanceFields(this);
        add(name, gender, species, breed, dateOfBirth, createButtonsLayout());


        cancel.addClickListener(e -> cancel());
        save.addClickListener(e -> {
            if (binder.isValid())
            {
                save();

            }
        });
        delete.addClickListener(e -> delete());

    }

    private HorizontalLayout createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        return new HorizontalLayout(save, delete, cancel);
    }


    private void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();

        petGrid.setItems(customer.getPets());

    }

    public void save() {

        customer.attachPet(pet);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        Notification.show("Operacja się powiodła!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        petGrid.setItems(customer.getPets());
    }

    private void delete() {
        customer.detachPet(pet);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();

        petGrid.setItems(customer.getPets());

    }

}
