package dev.malonso.springcontracttestdemo.events;

import dev.malonso.springcontracttestdemo.events.dto.Event;
import dev.malonso.springcontracttestdemo.events.dto.EventCreate;
import dev.malonso.springcontracttestdemo.events.dto.Ticket;
import dev.malonso.springcontracttestdemo.events.dto.TicketCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping({"","/"})
    public ResponseEntity<List<Event>> getEvents() {
        logger.debug("Received request GET:/events");
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable(value = "id") String id) {
        logger.debug("Received request GET:/events/" + id);
        return eventService.getEventByUid(id)
                .map(event -> ResponseEntity.ok(event)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping({"/",""})
    public ResponseEntity<Event> postEvent(UriComponentsBuilder uriComponentsBuilder,
                                           @RequestBody EventCreate eventCreate) {
        logger.debug("Received request POST:/events");
        Event event = this.eventService.addEvent(eventCreate);
        UriComponents uriComponents =
                uriComponentsBuilder.path("/payments/{id}")
                        .buildAndExpand(event.getUuid().toString());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PostMapping("/{id}/ticket")
    public ResponseEntity<Ticket> postTicket(UriComponentsBuilder uriComponentsBuilder,
                                             @PathVariable(value = "id") String id,
                                             @RequestBody TicketCreate ticketCreate) {
        logger.debug("Received request POST:/events/" + id + "/ticket");
        try {
            Optional<Ticket> ticket = this.eventService.addTicket(id, ticketCreate);
            if(ticket.isPresent()) {
                UriComponents uriComponents =
                        uriComponentsBuilder.path("/payments/{id}").buildAndExpand(ticket.get().getUuid().toString());
                return ResponseEntity.created(uriComponents.toUri()).build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (WebClientResponseException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }


}
