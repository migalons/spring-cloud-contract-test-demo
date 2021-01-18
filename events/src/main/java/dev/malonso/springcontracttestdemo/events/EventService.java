package dev.malonso.springcontracttestdemo.events;

import dev.malonso.springcontracttestdemo.events.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventService {

    Logger logger = LoggerFactory.getLogger(EventService.class);

    private final List<Event> eventList = new ArrayList<>();

    @Autowired
    protected PaymentsClient paymentsClient;

    public EventService() {
        eventList.add(new Event("Commit Conf", "Madrid", LocalDate.parse("2021-03-17"),200.00, List.of(new SponsorType("Gold", 500.00), new SponsorType("Platinum", 1000.00))));
        eventList.add(new Event("Dublin Tech conf", "Dublin", LocalDate.parse("2021-09-01"), 400.00, List.of(new SponsorType("Gold", 1500.00), new SponsorType("Platinum", 10000.00))));
        eventList.add(new Event("Cyprus JUG conf", "Limassol", LocalDate.parse("2021-03-01"), 300.00, List.of(new SponsorType("Gold", 1500.00))));
        eventList.add(new Event("Dev Con 2021", "Pune", LocalDate.parse("2021-10-01"), 90.00, List.of(new SponsorType("Gold", 1500.00))));
        eventList.add(new Event("Dev Fest 2021", "Mexico city", LocalDate.parse("2021-08-01"), 190.00, List.of(new SponsorType("Gold", 1500.00))));
    }

    public List<Event> getAllEvents() {
        return this.eventList;
    }

    public Optional<Event> getEventByUid(String id) {
        return getElement(UUID.fromString(id));
    }

    public Event addEvent(EventCreate eventCreate) {
        Event event = new Event(eventCreate);
        eventList.add(event);
        return event;
    }

    public Optional<Ticket> addTicket(String eventId, TicketCreate ticketCreate)
            throws WebClientResponseException {
        Optional<Event> eventOptional = getElement(UUID.fromString(eventId));
        if(eventOptional.isEmpty()) return Optional.empty();

        PaymentRequest paymentRequest =  new PaymentRequest(ticketCreate.getPaymentDetails().getHolder(), ticketCreate.getPaymentDetails().getNumber(), ticketCreate.getPaymentDetails().getCvc(),
                ticketCreate.getPaymentDetails().getExpiration(), eventOptional.get().getPrice());

        ResponseEntity<PaymentResponse> paymentResponseResponseEntity = paymentsClient.postPayment(paymentRequest);

        Ticket ticket = new Ticket(ticketCreate);
        return Optional.of(ticket);

    }

    private Optional<Event> getElement(UUID uuid) {
        return eventList.stream().filter(event -> event.getUuid().equals(uuid)).findFirst();
    }


}
