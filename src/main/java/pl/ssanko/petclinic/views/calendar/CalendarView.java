package pl.ssanko.petclinic.views.calendar;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.vaadin.stefan.fullcalendar.BusinessHours;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import pl.ssanko.petclinic.views.MainLayout;

import java.time.LocalDate;
import java.time.LocalTime;

@PageTitle("Calendar")
@Route(value = "calendar", layout = MainLayout.class)
@PermitAll
public class CalendarView extends VerticalLayout {

    public CalendarView() {
        FullCalendar calendar = FullCalendarBuilder.create().build();
        calendar.setSizeFull();
        calendar.setHeight(900);
        calendar.setHeightByParent();
        add(calendar);
        setFlexGrow(1, calendar);
        setAlignItems(FlexComponent.Alignment.STRETCH);

        calendar.setNowIndicatorShown(true);
        calendar.setNumberClickable(true);
        calendar.setTimeslotsSelectable(true);

        calendar.setSlotMinTime(LocalTime.of(8, 0));
        calendar.setSlotMaxTime(LocalTime.of(17, 0));

        Entry entry = new Entry();
        entry.setTitle("Some event");
        entry.setColor("#ff3333");

// the given times will be interpreted as utc based - useful when the times are fetched from your database
        entry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 0));
        entry.setEnd(entry.getStart().plusHours(2));

        calendar.addEntry(entry);

//        calendar.addEntryClickedListener(event -> {
//            Dialog dialog = new Dialog();
////            CalendarEntryView calendarEntryView = new CalendarEntryView(event.getEntry());
////            dialog.add(calendarEntryView);
//            dialog.setCloseOnEsc(true);
//            dialog.setCloseOnOutsideClick(true);
//            dialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
//            dialog.setWidth("500px");
//            dialog.open();
//        });

        calendar.setBusinessHours(
                new BusinessHours(LocalTime.of(8, 0), LocalTime.of(17, 0), BusinessHours.DEFAULT_BUSINESS_WEEK)
        );

        calendar.addTimeslotsSelectedListener((event) -> {
            // react on the selected timeslot, for instance create a new instance and let the user edit it
            Entry entry1 = new Entry();

            entry1.setStart(event.getStart()); // also event times are always utc based
            entry1.setEnd(event.getEnd());
            entry1.setAllDay(event.isAllDay());
            Dialog dialog = new Dialog();
//            CalendarEntryView calendarEntryView = new CalendarEntryView(event.getEntry());
//            dialog.add(calendarEntryView);
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(true);
            dialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
            dialog.setWidth("500px");
            dialog.open();

            entry1.setColor("dodgerblue");

            // ... show and editor
        });
    }

}
