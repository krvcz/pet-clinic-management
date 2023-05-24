package pl.ssanko.petclinic.views.home.component;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.ssanko.petclinic.data.dto.StatsDto;
import pl.ssanko.petclinic.data.entity.Visit;
import pl.ssanko.petclinic.views.visit.component.VisitCommonComponent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MainPageCommonComponent {

    public static VerticalLayout createStatsCardMainInfo(StatsDto statsDto) {
        // utworzenie komponentów
        H1 numberOfProcessingVisits = new H1(statsDto.getNumberOfProcessingVisits().toString());
        H1 numberOfVisitInLast24Hours = new H1(statsDto.getNumberOfVisitInLast24Hours().toString());
        H1 numberOfEventToday = new H1(statsDto.getNumberOfEventToday().toString());


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
        layout1.add(new VerticalLayout(new H6("Procesowane wizyty:"), numberOfProcessingVisits ));
        layout3.add(new VerticalLayout(new H6("Liczba wizyt w ostatnich 24 godzinach:"), numberOfVisitInLast24Hours));
        layout2.add(new VerticalLayout(new H6("Liczba dzisiejszych wydarzeń:"), numberOfEventToday));



        // stylowanie zawartości karty
        layout1.addClassName("content");
        layout2.addClassName("content");
        layout3.addClassName("content");


        horizontalLayout.add(layout3, layout2, layout1);


        // Create components for top section
        horizontalLayout.setWidthFull();
        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout);

        return verticalLayout;


    }

    public static VerticalLayout createStatsCardOverallInfo(StatsDto statsDto) {
        // utworzenie komponentów
        H1 numberOfVisitOnPaymentStatus = new H1(statsDto.getNumberOfVisitOnPaymentStatus().toString());
        H1 numberOfMedicines = new H1(statsDto.getNumberOfMedicines().toString());
        H1 numberOfMedicalProcedures = new H1(statsDto.getNumberOfMedicalProcedures().toString());


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
        layout1.add(new VerticalLayout(new H6("Wizyty w statusie rozliczenia:"), numberOfVisitOnPaymentStatus));
        layout3.add(new VerticalLayout(new H6("Całkowita liczba leków:"), numberOfMedicines));
        layout2.add(new VerticalLayout(new H6("Całkowita liczba procedur:"), numberOfMedicalProcedures));



        // stylowanie zawartości karty
        layout1.addClassName("cardmain");
        layout2.addClassName("cardmain");
        layout3.addClassName("cardmain");


        horizontalLayout.add(layout1, layout3, layout2);


        // Create components for top section
        horizontalLayout.setWidthFull();
        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout);

        return verticalLayout;

    }
}
