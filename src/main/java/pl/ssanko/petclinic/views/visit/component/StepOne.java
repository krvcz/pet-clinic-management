package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Veterinarian;
import pl.ssanko.petclinic.data.service.VeterinarianService;


public class StepOne extends Step{
    private final Integer ORDER = 1;

    private final Icon ICON = VaadinIcon.DOCTOR.create();

    private final Span NAME = new Span("1. Wybór specjalisty");

    private VeterinarianService veterinarianService;

    private Grid<Veterinarian> veterinarianGrid;

    private TextField filterTextField;

    private VerticalLayout verticalLayout;

    public StepOne(VeterinarianService veterinarianService) {
        this.veterinarianService = veterinarianService;
    }

    public StepOne() {

    }
    @Override
    public void configure() {
        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
//        filterTextField.addValueChangeListener(e -> updateVeterinarianGrid());


        // Grid z klientami
        veterinarianGrid = new Grid<>(Veterinarian.class);
        veterinarianGrid.setColumns("firstName", "lastName", "specialization");
        veterinarianGrid.getColumnByKey("firstName").setHeader("Imię");
        veterinarianGrid.getColumnByKey("lastName").setHeader("Nazwisko");
        veterinarianGrid.getColumnByKey("specialization").setHeader("Specjalizacja");
        veterinarianGrid.setItems(query -> veterinarianService.getAllVeterinarians(PageRequest.of(query.getPage(), query.getPageSize())));
        veterinarianGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        // Przycisk wyboru
        selectButton = new Button("Dalej");
        selectButton.setEnabled(false);
        selectButton.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
        selectButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layout.add(selectButton);


        // Listener dla Grida klientów
        veterinarianGrid.asSingleSelect().addValueChangeListener(event -> {
            selectButton.setEnabled(event.getValue() != null);
        });

        // Listener dla przycisku wyboru
        selectButton.addClickListener(event -> {
            veterinarian = veterinarianGrid.asSingleSelect().getValue();
            stepper.next();
        });

        veterinarianGrid.select(veterinarian);


        verticalLayout =  new VerticalLayout(filterTextField, veterinarianGrid, layout);
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

//    private void updateVeterinarianGrid() {
//        String filter = filterTextField.getValue();
//        veterinarianGrid.setItems(query ->
//                veterinarianService.getAllVeterinarians(PageRequest.of(query.getPage(), query.getPageSize()), filter));
//    }
}
