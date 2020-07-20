package com.challenge.users.controllers;

import com.challenge.users.DTO.EventDTO;
import com.challenge.users.models.Event;
import com.challenge.users.payloads.responses.MessageResponse;
import com.challenge.users.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> index(Principal principal) {
        return ResponseEntity.ok(eventService.getAllEventsByUser(principal.getName()));
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> get(@PathVariable("id") String eventId) {
        return ResponseEntity.ok(eventService.getEvents(eventId));
    }

    @PostMapping("/events")
    public ResponseEntity<?> store(@Valid @RequestBody EventDTO eventDTO, Principal principal) {

        return ResponseEntity.ok(eventService.createEvent(
                new Event(null, eventDTO.getTitle(), eventDTO.getDate()), principal.getName()));
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable("id") String eventId,@Valid @RequestBody EventDTO eventDTO) {

        return ResponseEntity.ok(eventService.updateEvent(eventId,
                new Event(null, eventDTO.getTitle(), eventDTO.getDate())));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") String eventId, Principal principal) {
        eventService.deleteEvent(eventId, principal.getName());
        return ResponseEntity.ok(new MessageResponse("Event successfully removed"));
    }
}
