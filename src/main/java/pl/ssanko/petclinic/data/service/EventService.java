package pl.ssanko.petclinic.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ssanko.petclinic.data.entity.Event;
import pl.ssanko.petclinic.data.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<Event> getEvents() {

        return  eventRepository.findAll();
    }

    @Transactional
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    @Transactional(readOnly = true)
    public Optional<Event> getAppointment(Long eventId) {
        return eventRepository.findByIdAndTypeEquals(eventId, "WIZYTA");
    }
}
