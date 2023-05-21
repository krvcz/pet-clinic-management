package pl.ssanko.petclinic.views.visit.component;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.ssanko.petclinic.data.entity.Visit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VisitCommonComponent {


    public static HorizontalLayout createStatusIcon(String status) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Span span;
        Icon icon;

        if (status.equals("W trakcie")) {
            icon = VaadinIcon.DOT_CIRCLE.create();
            span = new Span();
            span.add(icon);
            span.add("W trakcie");
            span.getElement().getThemeList().add("badge success");
            horizontalLayout.add(span);

        } else if ((status.equals("Rozliczenie"))) {
            icon = VaadinIcon.CASH.create();
            span = new Span();
            span.add(icon);
            span.add("Rozliczenie");
            span.getElement().getThemeList().add("badge warning");
            horizontalLayout.add(span);
        } else  {
            icon = VaadinIcon.CLOSE_SMALL.create();
            span = new Span();
            span.add(icon);
            span.add("Zakończona");
            span.getElement().getThemeList().add("badge error");
            horizontalLayout.add(span);
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
//        icon.getStyle().set("width", "24px");
//        icon.getStyle().set("height", "24px");
        return horizontalLayout;
    }

    public static VerticalLayout  createCardsInfo(Visit visit) {
        // utworzenie komponentów
        Label visitNumberLabel = new Label("Wizyta nr " + visit.getId());
        Label customerIdLabel = new Label("Id: " + String.valueOf(visit.getPet().getCustomer().getId()));
        Label customerNameLabel = new Label("Imię i nazwisko: " + visit.getPet().getCustomer().getFirstName() + " " + visit.getPet().getCustomer().getLastName());
        Label customerPhoneLabel = new Label("Telefon: " + visit.getPet().getCustomer().getPhoneNumber());
        Label customerEmailLabel = new Label("Email: " + visit.getPet().getCustomer().getEmail());
        Label veterinarianIdLabel = new Label("Id: " + String.valueOf(visit.getVeterinarian().getId()));
        Label veterinarianNameLabel = new Label("Imię i nazwisko: " + visit.getVeterinarian().getFirstName() + " " + visit.getVeterinarian().getLastName());
        Label veterinarianSpecializationLabel = new Label("Specjalizacja: " + visit.getVeterinarian().getSpecialization());

        Label petBreedLabel = new Label("Rasa: " + visit.getPet().getBreed());
        Label petSpeciesLabel = new Label("Gatunek: " + visit.getPet().getSpecies());
        Label petNameLabel = new Label("Imię: " + visit.getPet().getName());
        Label petGenderLabel = new Label("Płeć: " + visit.getPet().getGender());
        Label petDateOfBirthLabel = new Label("Data urodzenia: " + visit.getPet().getDateOfBirth() + " (" + ChronoUnit.YEARS.between(visit.getPet().getDateOfBirth(), LocalDate.now()) + " lat)");
        Label petIdLabel = new Label("Id: " + visit.getPet().getId());

        // ...

// utworzenie layoutu
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout layout1 = new VerticalLayout();
        layout1.addClassName("card");
        layout1.setMaxHeight("500px");
        layout1.setMargin(true);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.addClassName("card");
        layout2.setMaxHeight("500px");

        layout2.setMargin(true);

        VerticalLayout layout3 = new VerticalLayout();
        layout3.addClassName("card");
        layout3.setMaxHeight("500px");
        layout3.setMargin(true);


// dodanie komponentów do layoutu
        layout1.add(new VerticalLayout(new H3("Zwierzę:"), petIdLabel, petNameLabel, petSpeciesLabel, petBreedLabel, petGenderLabel, petDateOfBirthLabel));
        layout2.add(new VerticalLayout(new H3("Właściciel:"), customerIdLabel, customerNameLabel, customerPhoneLabel,  customerEmailLabel));
        layout3.add(new VerticalLayout(new H3("Specjalista:"), veterinarianIdLabel, veterinarianNameLabel, veterinarianSpecializationLabel));

        // stylowanie zawartości karty
        layout1.addClassName("content");
        layout2.addClassName("content");
        layout3.addClassName("content");

        horizontalLayout.add(layout1, layout2, layout3);


        HorizontalLayout visitInfo = new HorizontalLayout(visitNumberLabel, VisitCommonComponent.createStatusIcon(visit.getStatus()));
        visitInfo.setWidthFull();
        // Create components for top section
        horizontalLayout.setWidthFull();
        VerticalLayout verticalLayout = new VerticalLayout(visitInfo, horizontalLayout);

        return verticalLayout;

    }

}
