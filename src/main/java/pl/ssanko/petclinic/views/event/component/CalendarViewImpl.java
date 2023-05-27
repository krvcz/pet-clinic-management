package pl.ssanko.petclinic.views.event.component;

import org.vaadin.stefan.fullcalendar.CalendarView;

public enum CalendarViewImpl implements CalendarView {
    DAY_GRID_MONTH("dayGridMonth", "Siatka miesięczna"),
    TIME_GRID_DAY("timeGridDay", "Siatka dzienna"),
    TIME_GRID_WEEK("timeGridWeek", "Siatka tygodniowa"),
    LIST_WEEK("listWeek", "Lista tygodniowa"),
    LIST_DAY("listDay", "Lista dzienna"),
    LIST_MONTH("listMonth", "Lista miesięczna"),
    LIST_YEAR("listYear", "Lista roczna");

    private final String clientSideValue;

    private final String headerName;

    public String getName() {
        return this.name();
    }

    public String getClientSideValue() {
        return this.clientSideValue;
    }

    private CalendarViewImpl(String clientSideValue, String headerName) {

        this.clientSideValue = clientSideValue;
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}