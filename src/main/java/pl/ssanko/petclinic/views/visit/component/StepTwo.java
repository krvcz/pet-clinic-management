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
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Species;
import pl.ssanko.petclinic.data.entity.Veterinarian;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.SpeciesService;
import pl.ssanko.petclinic.data.service.VeterinarianService;
import pl.ssanko.petclinic.views.customer.component.CustomerAddForm;
import pl.ssanko.petclinic.views.customer.component.CustomerForm;

public class StepTwo extends Step {
    private final Integer ORDER = 2;

    private final Icon ICON = VaadinIcon.CLIPBOARD_USER.create();

    private final Span NAME = new Span("2. Wybór klienta");

    private final CustomerService customerService;

    private final PetService petService;

    private final SpeciesService speciesService;

    private Grid<Customer> customerGrid;

    private TextField filterTextField;

    private VerticalLayout verticalLayout;

    private Button addCustomerButton;


    public StepTwo(CustomerService customerService, PetService petService, SpeciesService speciesService) {
        this.customerService = customerService;
        this.petService = petService;
        this.speciesService = speciesService;
    }


    @Override
    public void configure() {

        //Dodanie przycisku dodania
        addCustomerButton = new Button("Dodaj nowego klienta", e -> showCustomerForm(new Customer()));

        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.addValueChangeListener(e -> updateCustomerGrid());

        // Grid z klientami
        customerGrid = new Grid<>(Customer.class);
        customerGrid.setColumns("firstName", "lastName", "email", "phoneNumber");
        customerGrid.getColumnByKey("firstName").setHeader("Imię");
        customerGrid.getColumnByKey("lastName").setHeader("Nazwisko");
        customerGrid.getColumnByKey("phoneNumber").setHeader("Numer telefonu");
        customerGrid.setItems(query -> customerService.getAllCustomers(PageRequest.of(query.getPage(), query.getPageSize())));
        customerGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        // Przycisk wyboru
        selectButton = new Button("Dalej");
        selectButton.setEnabled(false);
        selectButton.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
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
        customerGrid.asSingleSelect().addValueChangeListener(event -> {
            selectButton.setEnabled(event.getValue() != null);
        });

        // Listener dla przycisku wyboru
        selectButton.addClickListener(event -> {
            customer = customerGrid.asSingleSelect().getValue();
            stepper.next();

        });

        backButton.addClickListener(event -> {
            stepper.back();
    });

        customerGrid.select(customer);

        verticalLayout =  new VerticalLayout(new HorizontalLayout(filterTextField, addCustomerButton), customerGrid, layout);
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

    private void showCustomerForm(Customer customer) {
        CustomerForm customerForm = new CustomerAddForm(customerGrid, customerService, petService, speciesService, customer);

        Dialog dialog = new Dialog();
        dialog.add(customerForm);

        dialog.open();

    }

    public void updateCustomerGrid() {
        String filter = filterTextField.getValue();
        customerGrid.setItems(query ->
                customerService.getCustomersByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter));
    }



}
