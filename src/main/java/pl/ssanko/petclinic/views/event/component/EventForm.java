package pl.ssanko.petclinic.views.event.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import pl.ssanko.petclinic.data.common.EventType;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.service.EventService;
import pl.ssanko.petclinic.views.event.EventView;
import pl.ssanko.petclinic.views.visit.VisitPreProcessView;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EventForm extends FormLayout {

    private TextField description = new TextField("Komentarz");

    private IntegerField duration = new IntegerField("Przybliżony czas trwania (w minutach)");

    private DateTimePicker date = new DateTimePicker("Data rozpoczęcia");

    private ComboBox<String> type = new ComboBox<>("Typ zdarzenia");

    private Button save = new Button("Zapisz");

    private Button cancel = new Button("Anuluj");

    private Button delete = new Button("Usuń");

    private Button startVisit = new Button("Rozpocznij wizytę");

    private EventService eventService;

    private FullCalendar calendar;

    private Event event;

    private EventView eventView;

    private BeanValidationBinder<Event> binder = new BeanValidationBinder<>(Event.class);

    public EventForm(EventView eventView, EventService eventService, Event event, FullCalendar calendar) {
        this.eventView = eventView;
        this.eventService = eventService;
        this.event = event;
        this.calendar = calendar;


        binder.setBean(event);

        if (event == null) {
            setVisible(false);
        } else {
            setVisible(true);
            description.focus();
        }


        type.setItems(Arrays.stream(EventType.values()).map(Enum::toString).collect(Collectors.toSet()));

        binder.bindInstanceFields(this);

        startVisit.setVisible(false);
        startVisit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        setColspan(startVisit, 4);

        if (event.getType() != null) {
            startVisit.setVisible(event.getType().equals("WIZYTA"));
        }

        startVisit.addClickListener(e -> {
            UI.getCurrent().navigate(VisitPreProcessView.class, event.getId());
            Dialog dialog = (Dialog) getParent().get();
            dialog.close();
        });
        add(startVisit, type, description, date, duration, createButtonsLayout());

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(e -> cancel());
        save.addClickListener(e -> {
            if (binder.isValid())
            {
                save();

            }
        });
        delete.addClickListener(e -> delete());

    }


    private HorizontalLayout createButtonsLayout() {
        return new HorizontalLayout(save, delete, cancel);
    }


    private void cancel() {
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();

    }

    private void save() {

        eventService.createEvent(event);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        eventView.refreshCalendar();
        Notification.show("Operacja się powiodła!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);


    }

    private void delete() {
        eventService.deleteEvent(event);
        Dialog dialog = (Dialog) getParent().get();
        dialog.close();
        eventView.refreshCalendar();

    }



}
