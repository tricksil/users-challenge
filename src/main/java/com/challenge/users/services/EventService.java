package com.challenge.users.services;

import com.challenge.users.DTO.EventDTO;
import com.challenge.users.DTO.UserDTO;
import com.challenge.users.exception.EventExistsException;
import com.challenge.users.exception.EventNotFoundException;
import com.challenge.users.models.Event;
import com.challenge.users.models.User;
import com.challenge.users.repositories.EventRepository;
import com.challenge.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public EventDTO createEvent(Event event, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        if(eventRepository.existsByDate(event.getDate())){
            throw new EventExistsException("Event already exists");
        }
        event.setUser(user);
        event = eventRepository.save(event);

        return createEventDto(event);
    }

    public List<EventDTO> getAllEventsByUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        List<EventDTO> eventDTOS = new ArrayList<>();
        for (Event event : eventRepository.findAllByUserOrderByDateDesc(user).get()) {
            eventDTOS.add(createEventDto(event));
        }
        return eventDTOS;
    }

    public EventDTO getEvents(String eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found!"));

        return createEventDto(event);
    }

    public EventDTO updateEvent(String eventId, Event eventUpdate) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found!"));

        event.setTitle(eventUpdate.getTitle());
        event.setDate(eventUpdate.getDate());
        return createEventDto(eventRepository.save(event));
    }

    public void deleteEvent(String eventId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        eventRepository.deleteByIdAndUser(eventId, user);
    }

    public void deleteAllEventByUser(User user) {
        eventRepository.deleteAllByUser(user);
    }


    private EventDTO createEventDto(Event event) {
        return new EventDTO(event.getId(), event.getTitle(), event.getDate(),
                new UserDTO(event.getUser().getId(), event.getUser().getName(), event.getUser().getEmail()));
    }

}
