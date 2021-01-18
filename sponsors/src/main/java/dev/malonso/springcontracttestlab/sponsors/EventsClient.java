package dev.malonso.springcontracttestlab.sponsors;

import dev.malonso.springcontracttestlab.sponsors.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class EventsClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private WebClient webClient;

    public EventsClient(String protocol, String host, String port) {
        logger.debug("EventsWebClient base url: " + protocol + "://" + host + ":" + port);
        this.webClient = WebClient.builder().baseUrl(protocol + "://" + host + ":" + port).build();
    }

    public ResponseEntity<Event> getEvent(String eventId)
            throws WebClientResponseException {
        logger.debug("Send GET /events/{}", eventId);
            return webClient.get().uri("/events/" + eventId)
                    .retrieve()
                    .toEntity(Event.class)
                    .block();
    }
}
