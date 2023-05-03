package pl.ssanko.petclinic.data.service;

import org.springframework.stereotype.Service;
import org.vaadin.stefan.fullcalendar.Entry;
import pl.ssanko.petclinic.data.common.EventType;
import pl.ssanko.petclinic.data.entity.Event;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Service
public class EventMapper {

    public Entry map(Event event) {
        Entry entry = new Entry();

        entry.setGroupId(event.getId().toString());
        entry.setTitle(event.getDescription());
        entry.setStart(event.getDate());
        entry.setEnd(event.getDate().plusMinutes(event.getDuration()));
        entry.setColor(EventType.valueOf(event.getType()).getColor());

        return entry;

    }

    public Event map(Entry entry) {
        Event event = new Event();

        event.setId(Long.parseLong(entry.getGroupId()));
        event.setDescription(entry.getTitle());
        event.setDate(entry.getStart());
        event.setDuration((int) ChronoUnit.MINUTES.between(entry.getStart(), entry.getEnd()));
        event.setType(String.valueOf(Arrays.stream(EventType.values())
                .filter(e -> e.getColor().equals(entry.getColor()))
                .findFirst()
                .orElse(EventType.INNE)));


        return event;

    }


}
