package pl.ssanko.petclinic.views.home.component;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.ssanko.petclinic.data.dto.StatsDto;

public class StatisticsPanel extends VerticalLayout {

    private H1 numberOfProcessingVisits;
    private H1 numberOfVisitInLast24Hours;
    private H1 numberOfEventToday;
    private H1 numberOfVisitOnPaymentStatus;
    private H1 numberOfMedicines;
    private H1 numberOfMedicalProcedures;
    private StatsDto statsDto;

    public StatisticsPanel(StatsDto statsDto){
        this.statsDto = statsDto;

        initialize();
    }

    private void initialize() {
        numberOfProcessingVisits = new H1(statsDto.getNumberOfProcessingVisits().toString());
        numberOfVisitInLast24Hours = new H1(statsDto.getNumberOfVisitInLast24Hours().toString());
        numberOfEventToday = new H1(statsDto.getNumberOfEventToday().toString());
        numberOfVisitOnPaymentStatus = new H1(statsDto.getNumberOfVisitOnPaymentStatus().toString());
        numberOfMedicines = new H1(statsDto.getNumberOfMedicines().toString());
        numberOfMedicalProcedures = new H1(statsDto.getNumberOfMedicalProcedures().toString());

        HorizontalLayout numberOfProcessingVisitsLayout = new HorizontalLayout(numberOfProcessingVisits);
        numberOfProcessingVisitsLayout.addClassName("card");
        numberOfProcessingVisitsLayout.setMaxHeight("800px");
        numberOfProcessingVisitsLayout.setWidthFull();
        numberOfProcessingVisitsLayout.setMargin(true);

        HorizontalLayout numberOfVisitInLast24HoursLayout = new HorizontalLayout(numberOfVisitInLast24Hours);
        numberOfVisitInLast24HoursLayout.addClassName("card");
        numberOfVisitInLast24HoursLayout.setMaxHeight("800px");
        numberOfVisitInLast24HoursLayout.setWidthFull();
        numberOfVisitInLast24HoursLayout.setMargin(true);

        HorizontalLayout numberOfEventTodayLayout = new HorizontalLayout(numberOfEventToday);
        numberOfEventTodayLayout.addClassName("card");
        numberOfEventTodayLayout.setMaxHeight("800px");
        numberOfEventTodayLayout.setWidthFull();
        numberOfEventTodayLayout.setMargin(true);

        HorizontalLayout numberOfVisitOnPaymentStatusLayout = new HorizontalLayout(numberOfVisitOnPaymentStatus);
        numberOfVisitOnPaymentStatusLayout.addClassName("cardmain");
        numberOfVisitOnPaymentStatusLayout.setMaxHeight("800px");
        numberOfVisitOnPaymentStatusLayout.setWidthFull();
        numberOfVisitOnPaymentStatusLayout.setMargin(true);

        HorizontalLayout numberOfMedicinesLayout = new HorizontalLayout( numberOfMedicines);
        numberOfMedicinesLayout.addClassName("cardmain");
        numberOfMedicinesLayout.setMaxHeight("cardmain");
        numberOfMedicinesLayout.setMargin(true);
        numberOfMedicinesLayout.setWidthFull();

        HorizontalLayout numberOfMedicalProceduresLayout = new HorizontalLayout(numberOfMedicalProcedures);
        numberOfMedicalProceduresLayout.addClassName("cardmain");
        numberOfMedicalProceduresLayout.setMaxHeight("800px");
        numberOfMedicalProceduresLayout.setMargin(true);
        numberOfMedicalProceduresLayout.setWidthFull();

        numberOfProcessingVisitsLayout.add(new VerticalLayout(new H6("Procesowane wizyty:"), numberOfProcessingVisits));
        numberOfVisitInLast24HoursLayout.add(new VerticalLayout(new H6("Liczba wizyt w ostatnich 24 godzinach:"), numberOfVisitInLast24Hours));
        numberOfEventTodayLayout.add(new VerticalLayout(new H6("Liczba dzisiejszych wydarzeń:"), numberOfEventToday));
        numberOfVisitOnPaymentStatusLayout.add(new VerticalLayout(new H6("Wizyty w statusie rozliczenia:"), numberOfVisitOnPaymentStatus));
        numberOfMedicinesLayout.add(new VerticalLayout(new H6("Całkowita liczba leków:"), numberOfMedicines));
        numberOfMedicalProceduresLayout.add(new VerticalLayout(new H6("Całkowita liczba procedur:"), numberOfMedicalProcedures));

        HorizontalLayout firstRow = new HorizontalLayout(numberOfProcessingVisitsLayout, numberOfVisitInLast24HoursLayout, numberOfEventTodayLayout);
        firstRow.setWidthFull();

        HorizontalLayout secondRow = new HorizontalLayout(numberOfVisitOnPaymentStatusLayout, numberOfMedicinesLayout,numberOfMedicalProceduresLayout );
        secondRow.setWidthFull();

        add(firstRow, secondRow);
        setWidthFull();

    }

}
