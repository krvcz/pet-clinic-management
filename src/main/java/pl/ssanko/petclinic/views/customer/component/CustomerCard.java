package pl.ssanko.petclinic.views.customer.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;

public class CustomerCard extends VerticalLayout {

    private Customer customer;
    private CustomerStatsDto customerStatsDto;
    private Label customerIdLabel;
    private Label customerFirstNameLabel;
    private Label customerLastNameLabel;
    private Label customerPhoneLabel;
    private Label customerEmailLabel;
    private H1 numberOfVisits;
    private H1 numberOfPets;
    private Grid<Pet> petGrid;

    public CustomerCard (Customer customer, CustomerStatsDto customerStatsDto) {
        this.customer = customer;
        this.customerStatsDto = customerStatsDto;

        initialize();

    }

    private void initialize() {
        customerIdLabel = new Label("Id: " + customer.getId());
        customerFirstNameLabel = new Label("Imię: " + customer.getFirstName());
        customerLastNameLabel = new Label("Nazwisko: " + customer.getLastName());
        customerPhoneLabel = new Label("Telefon: " + customer.getPhoneNumber());
        customerEmailLabel = new Label("Email: " + customer.getEmail());
        numberOfVisits = new H1(customerStatsDto.getNumberOfVisits().toString());
        numberOfPets = new H1(customerStatsDto.getNumberOfPets().toString());


        HorizontalLayout horizontalLayout = new HorizontalLayout();

        VerticalLayout customerDetailsLayout = new VerticalLayout();
        customerDetailsLayout.addClassName("card");
        customerDetailsLayout.setMaxHeight("800px");
//        customerDetailsLayout.setMaxWidth("250px");
        customerDetailsLayout.setMargin(true);
        customerDetailsLayout.setWidthFull();

        customerDetailsLayout.add(new VerticalLayout(new H3("Klient:"), customerIdLabel, customerFirstNameLabel,
                customerLastNameLabel, customerPhoneLabel, customerEmailLabel));

        customerDetailsLayout.addClassName("content");

//        horizontalLayout.add(customerDetailsLayout);


        HorizontalLayout numberOfVisitsLayout = new HorizontalLayout(numberOfVisits);
        numberOfVisitsLayout.addClassName("cardmain");
        numberOfVisitsLayout.setMaxHeight("800px");
        numberOfVisitsLayout.setMargin(true);
        numberOfVisitsLayout.setWidthFull();

        HorizontalLayout numberOfPetsLayout = new HorizontalLayout(numberOfPets);
        numberOfPetsLayout.addClassName("cardmain");
        numberOfPetsLayout.setMaxHeight("800px");
        numberOfPetsLayout.setMargin(true);
        numberOfPetsLayout.setWidthFull();

        numberOfVisitsLayout.add(new VerticalLayout(new H3("Liczba wizyt:"), numberOfVisits));
        numberOfPetsLayout.add(new VerticalLayout(new H3("Liczba zwierząt:"), numberOfPets));

        petGrid = new Grid<>(Pet.class);
        petGrid.setSelectionMode(Grid.SelectionMode.NONE);
        petGrid.removeAllColumns();
        petGrid.addColumn(Pet::getName).setHeader("Nazwa");
        petGrid.addColumn(Pet::getSpecies).setHeader("Gatunek");
        petGrid.addComponentColumn(e -> {
            Button petDetail = new Button(new Icon(VaadinIcon.CLIPBOARD_HEART));
            petDetail.addThemeVariants(ButtonVariant.LUMO_LARGE);
            return petDetail;
        }).setHeader("Karta zwierzęcia");
        petGrid.setItems(customer.getPets());

        horizontalLayout.setWidthFull();
        setWidthFull();

        HorizontalLayout cardsLayout = new HorizontalLayout(customerDetailsLayout, numberOfVisitsLayout, numberOfPetsLayout);
        cardsLayout.setWidthFull();
        VerticalLayout layoutFull = new VerticalLayout(cardsLayout, petGrid);
        layoutFull.setWidthFull();

        horizontalLayout.add(layoutFull);
        add(horizontalLayout);
    }
}
