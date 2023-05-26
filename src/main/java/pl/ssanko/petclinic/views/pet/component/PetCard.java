package pl.ssanko.petclinic.views.pet.component;

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
import com.vaadin.flow.component.tabs.TabSheet;
import org.springframework.data.domain.PageRequest;
import pl.ssanko.petclinic.data.dto.CustomerStatsDto;
import pl.ssanko.petclinic.data.dto.PetStatsDto;
import pl.ssanko.petclinic.data.dto.VisitDto;
import pl.ssanko.petclinic.data.entity.Customer;
import pl.ssanko.petclinic.data.entity.Pet;
import pl.ssanko.petclinic.data.service.VisitService;
import pl.ssanko.petclinic.views.visit.component.VisitHistoryGrid;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PetCard extends VerticalLayout {

    private Pet pet;
    private PetStatsDto petStatsDto;

    private VisitService visitService;
    private Label petIdLabel;
    private Label petNameLabel;
    private Label petGenderLabel;
    private Label petBreedLabel;
    private Label petSpeciesLabel;
    private Label petdateOfBirthLabel;
    private Label customerIdLabel;
    private Label customerFirstNameLabel;
    private Label customerLastNameLabel;
    private Label customerPhoneLabel;
    private Label customerEmailLabel;
    private H1 numberOfVisits;

    private Grid<VisitDto> visitHistoryGrid;

    private TabSheet tabSheet;

    private VerticalLayout visitsHistoryLayout;

    public PetCard (Pet pet, PetStatsDto petStatsDto, VisitService visitService) {
        this.pet = pet;
        this.petStatsDto = petStatsDto;
        this.visitService = visitService;

        initialize();

    }

    private void initialize() {

        add(configureInfoCards(), configureTabSheet());
    }

    private HorizontalLayout configureInfoCards() {
        petIdLabel = new Label("Id: " + pet.getId());
        petNameLabel = new Label("Nazwa: " + pet.getName());
        petGenderLabel = new Label("Płeć " + pet.getGender());
        petBreedLabel = new Label("Rasa: " + pet.getBreed());
        petSpeciesLabel = new Label("Gatunek " + pet.getSpecies());
        petdateOfBirthLabel = new Label("Data urodzin: " + pet.getDateOfBirth() + " (" + ChronoUnit.YEARS.between(pet.getDateOfBirth(), LocalDate.now()) + " lat)");


        customerIdLabel = new Label("Id: " + pet.getCustomer().getId());
        customerFirstNameLabel = new Label("Imię: " + pet.getCustomer().getFirstName());
        customerLastNameLabel = new Label("Nazwisko: " + pet.getCustomer().getLastName());
        customerPhoneLabel = new Label("Telefon: " + pet.getCustomer().getPhoneNumber());
        customerEmailLabel = new Label("Email: " + pet.getCustomer().getEmail());

        numberOfVisits = new H1(petStatsDto.getNumberOfVisits().toString());

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        VerticalLayout customerDetailsLayout = new VerticalLayout();
        customerDetailsLayout.addClassName("card");
        customerDetailsLayout.setMaxHeight("800px");
        customerDetailsLayout.setMargin(true);
        customerDetailsLayout.setWidthFull();

        customerDetailsLayout.add(new VerticalLayout(new H3("Właściciel:"), customerIdLabel, customerFirstNameLabel,
                customerLastNameLabel, customerPhoneLabel, customerEmailLabel));

        customerDetailsLayout.addClassName("content");



        VerticalLayout petDetailsLayout = new VerticalLayout();
        petDetailsLayout.addClassName("card");
        petDetailsLayout.setMaxHeight("800px");
        petDetailsLayout.setMargin(true);
        petDetailsLayout.setWidthFull();

        petDetailsLayout.add(new VerticalLayout(new H3("Zwierzę:"), petIdLabel, petNameLabel,
                petGenderLabel, petBreedLabel, petSpeciesLabel, petdateOfBirthLabel));

        petDetailsLayout.addClassName("content");


        HorizontalLayout numberOfVisitsLayout = new HorizontalLayout(numberOfVisits);
        numberOfVisitsLayout.addClassName("cardmain");
        numberOfVisitsLayout.setMaxHeight("800px");
        numberOfVisitsLayout.setMargin(true);
        numberOfVisitsLayout.setWidthFull();

        numberOfVisitsLayout.add(new VerticalLayout(new H3("Liczba wizyt:"), numberOfVisits));


        horizontalLayout.setWidthFull();
        setWidthFull();

        HorizontalLayout cardsLayout = new HorizontalLayout(petDetailsLayout, customerDetailsLayout, numberOfVisitsLayout);
        cardsLayout.setWidthFull();

        horizontalLayout.add(cardsLayout);

        return  horizontalLayout;
    }

    private void configureVisitsHistoryCard() {
        visitHistoryGrid = new VisitHistoryGrid();
        visitHistoryGrid.setItems(query -> visitService.getEntireInfoAboutVisitForPet(pet.getId(),
                PageRequest.of(query.getPage(), query.getPageSize())));

        this.visitsHistoryLayout.add(visitHistoryGrid);


    }

    private TabSheet configureTabSheet() {

        tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        visitsHistoryLayout = new VerticalLayout();

        tabSheet.add("Historia wizyt", visitsHistoryLayout);

        configureVisitsHistoryCard();



        return tabSheet;

    }
}
