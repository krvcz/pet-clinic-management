package pl.ssanko.petclinic.views.event.component;

import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.dataprovider.EagerInMemoryEntryProvider;
import org.vaadin.stefan.fullcalendar.dataprovider.EntryProvider;
import pl.ssanko.petclinic.data.common.EventType;
import pl.ssanko.petclinic.data.entity.*;
import pl.ssanko.petclinic.data.service.EventMapper;
import pl.ssanko.petclinic.data.service.EventService;
import pl.ssanko.petclinic.views.event.EventView;

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

        startVisit.setEnabled(false);
        startVisit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        setColspan(startVisit, 4);

        if (event.getType() != null) {
            startVisit.setEnabled(event.getType().equals("WIZYTA"));
        }
        add(startVisit, type, description, date, duration, createButtonsLayout());


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
