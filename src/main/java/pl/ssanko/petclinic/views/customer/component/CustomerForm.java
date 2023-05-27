package pl.ssanko.petclinic.views.customer.component;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;
import pl.ssanko.petclinic.views.pet.component.PetForm;

import java.time.LocalDate;

public abstract class CustomerForm extends FormLayout {

    protected TextField firstName = new TextField("Imię");

    protected TextField lastName = new TextField("Nazwisko");

    protected TextField email = new TextField ("Adres email");

    protected TextField phoneNumber = new TextField("Telefon");

    protected Button save = new Button("Zapisz");

    protected Button cancel = new Button("Anuluj");

    protected Button plusButton = new Button("Nowe zwierzę", new Icon(VaadinIcon.PLUS));

    protected Button editButton = new Button("Edytuj zwierzę", new Icon(VaadinIcon.EDIT));

    protected Button removeButton = new Button("Usuń zwierzę", new Icon(VaadinIcon.ERASER));

    protected Grid<Pet> petGrid = new Grid<>(Pet.class);

    private Tabs tabs = new Tabs();


    protected BeanValidationBinder<Customer> binder = new BeanValidationBinder<>(Customer.class);

    protected CustomerService customerService;

    protected PetService petService;

    protected SpeciesService speciesService;

    protected Customer customer;

    protected Grid<Customer> customerGrid;


    public CustomerForm(Grid<Customer> customerGrid, CustomerService customerService, PetService petService, SpeciesService speciesService, Customer customer) {
        this.customerGrid = customerGrid;
        this.customerService = customerService;
        this.customer = customer;
        this.speciesService = speciesService;
        this.petService = petService;


        binder.setBean(customer);

        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }

        binder.bindInstanceFields(this);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        petGrid.setColumns("name", "species", "breed", "gender", "dateOfBirth");
        petGrid.getColumnByKey("name").setHeader("Imię");
        petGrid.getColumnByKey("species").setHeader("Gatunek");
        petGrid.getColumnByKey("breed").setHeader("Rasa");
        petGrid.getColumnByKey("gender").setHeader("Płeć");
        petGrid.getColumnByKey("dateOfBirth").setHeader("Data urodzenia");

        petGrid.setColumns("name", "species", "breed", "gender", "dateOfBirth");
        petGrid.getColumnByKey("name").setHeader("Imię");
        petGrid.getColumnByKey("species").setHeader("Gatunek");
        petGrid.getColumnByKey("breed").setHeader("Rasa");
        petGrid.getColumnByKey("gender").setHeader("Płeć");
        petGrid.getColumnByKey("dateOfBirth").setHeader("Data urodzenia");


        add(firstName, lastName, email, phoneNumber, configurePetGrid(), createButtonsLayout());


        cancel.addClickListener(e -> cancel());
        save.addClickListener(e -> {
            if (binder.isValid())
            {
                save();

            }
        });

    }

    protected abstract void cancel();

    protected abstract void save();



    private HorizontalLayout createButtonsLayout() {
        return new HorizontalLayout(save, cancel);
    }

    private VerticalLayout configurePetGrid() {
        VerticalLayout pages = new VerticalLayout();
        pages.setSizeFull();


        setColspan(tabs, 2);
        setColspan(pages, 2);


        tabs.addSelectedChangeListener(e -> {
                    if (e.getSelectedTab().getLabel().equals("Pets")) {
                        pages.removeAll();
                        pages.add(petGrid);
                    }
                }

        );


        plusButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        plusButton.getElement().setAttribute("aria-label", "addPet");
        plusButton.addClickListener( e -> showPetForm(new Pet()));

        editButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        editButton.getElement().setAttribute("aria-label", "editPet");

        editButton.addClickListener(e -> {
                    if (petGrid.getSelectedItems().iterator().hasNext()) {
                        Pet pet = petGrid.getSelectedItems().iterator().next();
                        showPetForm(pet);
                    }

                });



        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeButton.getElement().setAttribute("aria-label", "deletePet");

        removeButton.addClickListener(e -> {
            Pet pet = petGrid.getSelectedItems().iterator().next();
            customer.getPets().remove(pet);
            petGrid.setItems(customer.getPets());
        });

        pages.add(new HorizontalLayout(plusButton, editButton, removeButton), petGrid);
        return  pages;
    }
    private void showPetForm(Pet pet) {
        PetForm petForm = new PetForm(petService, speciesService, petGrid, pet, customer);

        Dialog dialog = new Dialog();
        dialog.add(petForm);

        dialog.open();

    }

    protected void refreshGrid() {
        customerGrid.setItems(query ->
                customerService.getAllCustomers(PageRequest.of(query.getPage(), query.getPageSize())));
    }


}