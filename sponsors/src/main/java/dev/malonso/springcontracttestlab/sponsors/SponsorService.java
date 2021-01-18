package dev.malonso.springcontracttestlab.sponsors;

import dev.malonso.springcontracttestlab.sponsors.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SponsorService {

    @Autowired @Qualifier(value = "PaymentsClient")
    protected PaymentsClient paymentsClient;

    @Autowired @Qualifier(value = "EventsClient")
    protected EventsClient eventsClient;

    protected List<Sponsor> sponsorList;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SponsorService() {
        this.sponsorList = new ArrayList<>();
        this.addSponsor(new SponsorCreate("Umbrella Corp."));
        this.addSponsor(new SponsorCreate("Hydra Corp."));
    }

    public List<Sponsor> getSponsors() {
        return this.sponsorList;
    }

    public Optional<Sponsor> getSponsor(String id) {
        return this.sponsorList.stream().filter(s -> s.getUuid().toString().equals(id)).findFirst();
    }

    public Sponsor addSponsor(SponsorCreate sponsorCreate) {
        Sponsor sponsor = new Sponsor(sponsorCreate);
        sponsorList.add(sponsor);
        return sponsor;
    }

    public Optional<Sponsorship> addSponsorship(String sponsorId, SponsorshipCreate sponsorshipCreate)
            throws Exception {
        Optional<Sponsor> sponsorOptional = this.getSponsor(sponsorId);
        if(sponsorOptional.isEmpty()) return Optional.empty();

        ResponseEntity<Event> eventResponseEntity = eventsClient.getEvent(sponsorshipCreate.getEventId());
        Event event = eventResponseEntity.getBody();
        Optional<SponsorType> sponsorClass = event.getSponsorTypes().stream()
                .filter(c -> c.getName().equals(sponsorshipCreate.getType()))
                .findFirst();
        if(sponsorClass.isEmpty()) throw new Exception("Event not found");


        PaymentRequest paymentRequest =  new PaymentRequest(sponsorshipCreate.getPaymentDetails().getHolder(),
                sponsorshipCreate.getPaymentDetails().getNumber(), sponsorshipCreate.getPaymentDetails().getCvc(),
                sponsorshipCreate.getPaymentDetails().getExpiration(), sponsorClass.get().getPrice(), sponsorshipCreate.getNote());

        ResponseEntity<PaymentResponse> paymentResponseResponseEntity = paymentsClient.postPayment(paymentRequest);

        Sponsorship sponsorship = new Sponsorship();
        sponsorship.setEventId(eventResponseEntity.getBody().getUuid().toString());
        sponsorship.setSponsorType(sponsorshipCreate.getType());
        sponsorship.setPaymentId(paymentResponseResponseEntity.getBody().getUuid().toString());
        sponsorship.setPayDate(paymentResponseResponseEntity.getBody().getTimestamp());

        return Optional.of(sponsorship);

    }

}
