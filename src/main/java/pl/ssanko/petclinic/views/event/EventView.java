package pl.ssanko.petclinic.views.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.vaadin.stefan.fullcalendar.*;
import org.vaadin.stefan.fullcalendar.dataprovider.EagerInMemoryEntryProvider;
import org.vaadin.stefan.fullcalendar.dataprovider.EntryProvider;
import org.vaadin.stefan.fullcalendar.dataprovider.LazyInMemoryEntryProvider;
import pl.ssanko.petclinic.data.entity.Event;
import pl.ssanko.petclinic.data.service.EventMapper;
import pl.ssanko.petclinic.data.service.EventService;
import pl.ssanko.petclinic.views.MainLayout;
import pl.ssanko.petclinic.views.event.component.EventForm;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@PageTitle("Appointments")
@Route(value = "appointments", layout = MainLayout.class)
@PermitAll
public class EventView extends VerticalLayout {

    private final EventService eventService;

    protected FullCalendar calendar = FullCalendarBuilder.create().build();

    private Button newEventButton = new Button("Nowe zdarzenie", new Icon(VaadinIcon.FILE_ADD));

    private final EventMapper eventMapper;

    public EventView(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;


        add(configureToolBox(), configureCalendar());
    }

    private HorizontalLayout configureToolBox() {
        ComboBox<CalendarView> viewBox = new ComboBox<>("", CalendarViewImpl.values());
        viewBox.addValueChangeListener(e -> {
            CalendarView value = e.getValue();
            calendar.changeView(value == null ? CalendarViewImpl.LIST_MONTH : value);
        });
        viewBox.setValue(CalendarViewImpl.LIST_MONTH);


        newEventButton.addClickListener(event -> showEventForm(new Event()));


        return new HorizontalLayout(newEventButton, viewBox);
    }



    private FullCalendar configureCalendar() {
        calendar.onEnabledStateChanged(false);
        calendar.changeView(CalendarViewImpl.TIME_GRID_DAY);
        calendar.setSizeFull();
        calendar.setHeight(900);
        calendar.setHeightByParent();
        setFlexGrow(1, calendar);
        setAlignItems(Alignment.STRETCH);
        refreshCalendar();

        calendar.setLocale(getLocale());



        calendar.setBusinessHours(
                new BusinessHours(LocalTime.of(8, 0), LocalTime.of(17, 0), BusinessHours.DEFAULT_BUSINESS_WEEK)
        );

        calendar.addEntryClickedListener(event -> showEventForm(new EventMapper().map(event.getEntry())));

        return calendar;
    }

    private void showEventForm(Event event) {
        EventForm eventForm = new EventForm(this, eventService, event, calendar);

        Dialog dialog = new Dialog();
        dialog.add(eventForm);

        dialog.open();
    }

    public void refreshCalendar() {
        List<Entry> entries = eventService.getEvents().stream()
                .map(eventMapper::map)
                .toList();

        calendar.removeAllEntries();
        calendar.addEntries(entries);
    }


}
