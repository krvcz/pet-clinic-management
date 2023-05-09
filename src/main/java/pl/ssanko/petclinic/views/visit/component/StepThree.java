package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.service.PetService;

public class StepThree extends Step {
    private final Integer ORDER = 3;

    private final Icon ICON = VaadinIcon.STETHOSCOPE.create();

    private final Span NAME = new Span("3. Wybór zwierzęcia");

    private final PetService petService;

    private TextField filterTextField;

    private VerticalLayout verticalLayout;

    private Grid<Pet> petGrid;

    public StepThree(PetService petService) {
        this.petService = petService;
        configure();
    }


    @Override
    public void configure() {

        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
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
        selectButton.addClassName("green-button");


        // Przycisk wyboru
        backButton = new Button("Powrót");
        backButton.setIcon(VaadinIcon.ARROW_CIRCLE_LEFT.create());
        backButton.addClassName("red-button");

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
            stepper.next();
//            content.add(configureStepFour());

        });

        backButton.addClickListener(event -> {
            stepper.back();

        });

        verticalLayout =  new VerticalLayout(filterTextField, petGrid, layout);
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


    public void updatePetGrid() {
        String filter = filterTextField.getValue();
        petGrid.setItems(query ->
                petService.getPetsByCustomerWithFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter, customer.getId()));
    }

}
