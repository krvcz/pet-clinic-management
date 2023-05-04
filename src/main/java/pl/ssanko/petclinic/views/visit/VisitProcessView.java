package pl.ssanko.petclinic.views.visit;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
import pl.ssanko.petclinic.data.service.CustomerService;
import pl.ssanko.petclinic.views.MainLayout;


@PageTitle("Visits")
@Route(value = "visits/process", layout = MainLayout.class)
@PermitAll
public class VisitProcessView extends VerticalLayout {

    private Grid<Customer> customerGrid;
    private Button selectButton;
    private CustomerService customerService;
    private TextField filterTextField;

    public VisitProcessView(CustomerService customerService) {
        this.customerService = customerService;

        //tworzenie zakładek
        Tab tab1 = new Tab(VaadinIcon.DOCTOR.create(), new Span("1.Wybór specjalisty"));
        Tab tab2 = new Tab(VaadinIcon.CLIPBOARD_USER.create(), new Span("2.Wybór klienta"));
        Tab tab3 = new Tab(VaadinIcon.STETHOSCOPE.create(), new Span("3.Wybór zwierzęcia"));
        Tab tab4 = new Tab(VaadinIcon.DOCTOR_BRIEFCASE.create(), new Span("4.Wizyta"));
        Tab tab5 = new Tab(VaadinIcon.PIGGY_BANK_COIN.create(), new Span("5.Rozliczenie"));

        //tworzenie paska zakładek
        Tabs tabs = new Tabs(tab1, tab2, tab3, tab4, tab5);

        tab1.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab2.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab3.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab4.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab5.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);

        // Dodanie filtru do tabeli
        filterTextField = new TextField();
        filterTextField.setPlaceholder("Szukaj...");
        filterTextField.addValueChangeListener(e -> updateGrid());

        // Grid z klientami
        customerGrid = new Grid<>(Customer.class);
        customerGrid.setColumns("firstName", "lastName", "email", "phoneNumber");
        customerGrid.getColumnByKey("firstName").setHeader("Imię");
        customerGrid.getColumnByKey("lastName").setHeader("Adres Email");
        customerGrid.getColumnByKey("phoneNumber").setHeader("Numer telefonu");
        customerGrid.setItems(query -> customerService.getAllCustomers(PageRequest.of(query.getPage(), query.getPageSize())));
        customerGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        add(tabs, filterTextField, customerGrid);

        // Przycisk wyboru
        selectButton = new Button("Dalej");
        selectButton.setEnabled(false);
        selectButton.addClassName("green-button");
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        layout.add(selectButton);
        add(layout);

        // Listener dla Grida klientów
        customerGrid.asSingleSelect().addValueChangeListener(event -> {
            selectButton.setEnabled(event.getValue() != null);
        });

        // Listener dla przycisku wyboru
        selectButton.addClickListener(event -> {
            Customer selectedClient = customerGrid.asSingleSelect().getValue();
//            UI.getCurrent().navigate(VisitCustomerView.class);
            tab2.setSelected(true);
            tab1.setSelected(false);
        });

    }



    public void updateGrid() {
        String filter = filterTextField.getValue();
        customerGrid.setItems(query ->
                customerService.getCustomersByFilter(PageRequest.of(query.getPage(), query.getPageSize()), filter));
    }
}



