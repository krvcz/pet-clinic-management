package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouteParameters;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.customer.component.CustomerAddForm;
import pl.ssanko.petclinic.views.customer.component.CustomerForm;
import pl.ssanko.petclinic.views.pet.component.PetForm;
import pl.ssanko.petclinic.views.visit.VisitProcessView;

public class StepThree extends Step {
    private final Integer ORDER = 3;

    private final Icon ICON = VaadinIcon.STETHOSCOPE.create();

    private final Span NAME = new Span("3. Wybór zwierzęcia");

    private PetService petService;

    private SpeciesService speciesService;

    private  VisitService visitService;

    private TextField filterTextField;

    private VerticalLayout verticalLayout;

    private Grid<Pet> petGrid;

    private Button addPetButton;

    public StepThree(PetService petService, VisitService visitService, SpeciesService speciesService) {
        this.petService = petService;
        this.visitService = visitService;
        this.speciesService = speciesService;
    }

    public StepThree() {

    };

    @Override
    public void configure() {

        addPetButton = new Button("Dodaj nowe zwierze", e -> showPetForm(new Pet()));
        addPetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterTextField.addValueChangeListener(e -> updatePetGrid());

        // Grid z klientami
        petGrid = new Grid<>(Pet.class);
        petGrid.setColumns("name", "species", "breed", "gender", "dateOfBirth");
        petGrid.getColumnByKey("name").setHeader("Imię");
        petGrid.getColumnByKey("species").setHeader("Gatunek");
        petGrid.getColumnByKey("breed").setHeader("Rasa");
        petGrid.getColumnByKey("gender").setHeader("Płeć");
        petGrid.getColumnByKey("dateOfBirth").setHeader("Data urodzenia");
        if (customer != null) {
            petGrid.setItems(customer.getPets());
        }
        petGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        // Przycisk wyboru
        selectButton = new Button("Rozpocznij wizytę");
        selectButton.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
        selectButton.setEnabled(false);
        selectButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);


        // Przycisk wyboru
        backButton = new Button("Powrót");
        backButton.setIcon(VaadinIcon.ARROW_CIRCLE_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layout.add(backButton, selectButton);

        // Listener dla Grida klientów
        petGrid.asSingleSelect().addValueChangeListener(event -> {
            selectButton.setEnabled(event.getValue() != null);
        });

        // Listener dla przycisku wyboru
        selectButton.addClickListener(event -> {
            pet = petGrid.asSingleSelect().getValue();
            Visit visit = new Visit();
                    visit.setPet(pet);
                    visit.setVeterinarian(veterinarian);
                    visit.setStatus("W trakcie");
            Visit newVisit = visitService.addNewVisit(visit);
            selectButton.getUI().ifPresent(ui -> ui.navigate(
                    VisitProcessView.class, newVisit.getId()));
//            stepper.next();

        });

        backButton.addClickListener(event -> {
            stepper.back();

        });

        verticalLayout =  new VerticalLayout(new HorizontalLayout(filterTextField, addPetButton), petGrid, layout);
    }

    @Override
    public Integer getOrder() {
        return ORDER;
    }

    @Override
    public Icon getIcon() {
        return ICON;
    }

    @Override
    public Span getName() {
        return NAME;
    }

    @Override
    public Div getContent() {
        configure();
        return new Div(verticalLayout);
    }

    private void showPetForm(Pet pet) {
        PetForm petForm = new PetForm(petService, speciesService, petGrid, pet, customer) {
            @Override
            public void save() {
                customer.attachPet(pet);
                petService.addPet(pet);
                Dialog dialog = (Dialog) getParent().get();
                dialog.close();
                Notification.show("Operacja się powiodła!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                updatePetGrid();
            }
        };
        Dialog dialog = new Dialog();
        dialog.add(petForm);

        dialog.open();

    }


    public void updatePetGrid() {
        String filter = filterTextField.getValue();
        petGrid.setItems(query ->
                petService.getPetsByCustomerWithFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter, customer.getId()));
    }

}
