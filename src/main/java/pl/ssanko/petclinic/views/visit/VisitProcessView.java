package pl.ssanko.petclinic.views.visit;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.entity.Veterinarian;
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.data.service.PetService;
import pl.ssanko.petclinic.data.service.VeterinarianService;
import pl.ssanko.petclinic.views.MainLayout;


@PageTitle("Visits")
@Route(value = "visits/process", layout = MainLayout.class)
@PermitAll
public class VisitProcessView extends VerticalLayout {

    private Grid<Customer> customerGrid;
    private Grid<Veterinarian> veterinarianGrid;
    private Grid<Pet> petGrid;
    private Button selectButton;
    private Button backButton;
    private CustomerService customerService;
    private VeterinarianService veterinarianService;
    private PetService petService;
    private TextField filterTextField;
    private Div content;
    private Tab stepOne;
    private Tab stepTwo;
    private Tab stepThree;
    private Tab stepFour;
    private Tab stepFive;
    private Veterinarian veterinarian;
    private Customer customer;
    private Pet pet;

    public VisitProcessView(CustomerService customerService, VeterinarianService veterinarianService, PetService petService) {
        this.customerService = customerService;
        this.veterinarianService = veterinarianService;
        this.petService = petService;
        content = new Div();
        content.setWidthFull();
        content.add(configureStepOne());

        
        add(configureTabs(), content);

    }

    private VerticalLayout configureStepOne() {
        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.addValueChangeListener(e -> updateVeterinarianGrid());

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
        selectButton.addClassName("green-button");
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        layout.add(selectButton);


        // Listener dla Grida klientów
        veterinarianGrid.asSingleSelect().addValueChangeListener(event -> {
            selectButton.setEnabled(event.getValue() != null);
        });

        // Listener dla przycisku wyboru
        selectButton.addClickListener(event -> {
            veterinarian = veterinarianGrid.asSingleSelect().getValue();
            stepTwo.setSelected(true);
            stepOne.setSelected(false);
            content.removeAll();
            content.add(configureStepTwo());

        });

        return new VerticalLayout(filterTextField, veterinarianGrid, layout);
    }


    private VerticalLayout configureStepTwo() {

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
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        layout.add(selectButton);

        // Przycisk wyboru
        backButton = new Button("Powrót");
        backButton.setIcon(VaadinIcon.ARROW_CIRCLE_LEFT.create());
        backButton.addClassName("red-button");
        HorizontalLayout layoutBack = new HorizontalLayout();
        layoutBack.setWidthFull();
        layoutBack.setJustifyContentMode(JustifyContentMode.START);
        layoutBack.add(backButton);


        // Listener dla Grida klientów
        customerGrid.asSingleSelect().addValueChangeListener(event -> {
            selectButton.setEnabled(event.getValue() != null);
        });

        // Listener dla przycisku wyboru
        selectButton.addClickListener(event -> {
            customer = customerGrid.asSingleSelect().getValue();
            stepThree.setSelected(true);
            stepTwo.setSelected(false);
            content.removeAll();
            content.add(configureStepThree());

        });

        backButton.addClickListener(event -> {
            stepOne.setSelected(true);
            stepTwo.setSelected(false);
            content.removeAll();
            content.add(configureStepOne());
            veterinarianGrid.select(veterinarian);


        });

        return new VerticalLayout(filterTextField,customerGrid, new HorizontalLayout(layoutBack, layout));
    }

    private VerticalLayout configureStepThree() {

//        // Dodanie filtru do tabeli
//        filterTextField = new TextField();
//        filterTextField.setPlaceholder("Szukaj...");
//        filterTextField.addValueChangeListener(e -> updatePetGrid());

        // Grid z klientami
        petGrid = new Grid<>(Pet.class);
        petGrid.setColumns("name", "species", "breed", "gender", "dateOfBirth");
        petGrid.getColumnByKey("name").setHeader("Imię");
        petGrid.getColumnByKey("species").setHeader("Gatunek");
        petGrid.getColumnByKey("breed").setHeader("Rasa");
        petGrid.getColumnByKey("gender").setHeader("Płeć");
        petGrid.getColumnByKey("dateOfBirth").setHeader("Data urodzenia");
        petGrid.setItems(customer.getPets());
        petGrid.setSelectionMode(Grid.SelectionMode.SINGLE);


        // Przycisk wyboru
        selectButton = new Button("Dalej");
        selectButton.setIcon(VaadinIcon.ARROW_CIRCLE_RIGHT.create());
        selectButton.setEnabled(false);
        selectButton.addClassName("green-button");
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        layout.add(selectButton);

        // Przycisk wyboru
        backButton = new Button("Powrót");
        backButton.setIcon(VaadinIcon.ARROW_CIRCLE_LEFT.create());
        backButton.addClassName("red-button");
        HorizontalLayout layoutBack = new HorizontalLayout();
        layoutBack.setWidthFull();
        layoutBack.setJustifyContentMode(JustifyContentMode.START);
        layoutBack.add(backButton);


        // Listener dla Grida klientów
        petGrid.asSingleSelect().addValueChangeListener(event -> {
            selectButton.setEnabled(event.getValue() != null);
        });

        // Listener dla przycisku wyboru
        selectButton.addClickListener(event -> {
            pet = petGrid.asSingleSelect().getValue();
            stepFour.setSelected(true);
            stepThree.setSelected(false);
            content.removeAll();
//            content.add(configureStepFour());

        });

        backButton.addClickListener(event -> {
            stepTwo.setSelected(true);
            stepThree.setSelected(false);
            content.removeAll();
            content.add(configureStepTwo());
            customerGrid.select(customer);

        });

        return new VerticalLayout(petGrid, new HorizontalLayout(layoutBack, layout));
    }


    private HorizontalLayout configureTabs() {
        //utworzenie kontenera na tabsy
        HorizontalLayout tabsContainer = new HorizontalLayout();
        tabsContainer.setWidthFull();
        tabsContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);


        //tworzenie zakładek
        stepOne = new Tab(VaadinIcon.DOCTOR.create(), new Span("1. Wybór specjalisty"));
        stepTwo = new Tab(VaadinIcon.CLIPBOARD_USER.create(), new Span("2. Wybór klienta"));
        stepThree= new Tab(VaadinIcon.STETHOSCOPE.create(), new Span("3. Wybór zwierzęcia"));
        stepFour = new Tab(VaadinIcon.DOCTOR_BRIEFCASE.create(), new Span("4. Wizyta"));
        stepFive = new Tab(VaadinIcon.PIGGY_BANK_COIN.create(), new Span("5. Rozliczenie"));

        //tworzenie paska zakładek
        Tabs tabs = new Tabs(stepOne, stepTwo, stepThree, stepFour, stepFive);


        stepOne .addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        stepTwo.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        stepThree.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        stepFour.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        stepFive.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        tabsContainer.add(tabs);


//        tabs.addSelectedChangeListener(e -> {
//            Tab currentTab = e.getSelectedTab();
//            content.removeAll();
//            Component component = null;
//            if (currentTab.equals(stepOne)) {
//                 component = configureStepOne();
//            } else if (currentTab.equals(stepTwo)) {
//                component = configureStepTwo();
//            }
//         else if (currentTab.equals(stepThree)) {
//                component = configureStepThree();
//            }
////            } else if (currentTab.equals(stepFour)) {
////                component = configureStepFour();
////            } else if (currentTab.equals(stepFive)) {
////                component = configureStepFive();
////            }
//            content.add(component);
//            customerGrid.select(customer);
//            petGrid.select(pet);
//            veterinarianGrid.select(veterinarian);
//
//        });
//        tabs.setSelectedTabIndicatorVisible(false);



        return tabsContainer;
    }



    public void updateCustomerGrid() {
        String filter = filterTextField.getValue();
        customerGrid.setItems(query ->
                customerService.getCustomersByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter));
    }

    public void updateVeterinarianGrid() {
        String filter = filterTextField.getValue();
        customerGrid.setItems(query ->
                customerService.getCustomersByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter));
    }
}



