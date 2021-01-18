package dev.malonso.springcontracttestlab.sponsors;

import dev.malonso.springcontracttestlab.sponsors.dto.Sponsor;
import dev.malonso.springcontracttestlab.sponsors.dto.SponsorCreate;
import dev.malonso.springcontracttestlab.sponsors.dto.Sponsorship;
import dev.malonso.springcontracttestlab.sponsors.dto.SponsorshipCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SponsorController {

    private SponsorService sponsorService;

    @Autowired
    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping("/sponsors")
    public ResponseEntity<List<Sponsor>> getSponsors() {
        return new ResponseEntity<>(sponsorService.getSponsors(), HttpStatus.OK);
    }

    @GetMapping("/sponsors/{id}")
    public ResponseEntity<Sponsor> getSponsorById(@PathVariable("id") String id) {
        Optional<Sponsor> optionalSponsor = sponsorService.getSponsor(id);
        if (optionalSponsor.isPresent()) {
            return new ResponseEntity<>(optionalSponsor.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/sponsors")
    public ResponseEntity<Sponsor> postSponsor(@RequestBody SponsorCreate sponsorCreate) {
        Sponsor sponsor = sponsorService.addSponsor(sponsorCreate);
        return new ResponseEntity(sponsor, HttpStatus.OK);
    }

    @PostMapping("/sponsors/{id}/sponsorships")
    public ResponseEntity<Sponsorship> postSponsor(UriComponentsBuilder uriComponentsBuilder,
                                                   @PathVariable("id") String sponsorId,
                                                   @RequestBody @Valid SponsorshipCreate sponsorShipCreate) {
        try {
            Optional<Sponsorship> sponsorShip = sponsorService.addSponsorship(sponsorId, sponsorShipCreate);
            if(sponsorShip.isEmpty()) return ResponseEntity.notFound().build();

            UriComponents uriComponents =
                    uriComponentsBuilder.path("/sponsors/{sponsor}/sponsorships/{sponsorship}").buildAndExpand(sponsorId, sponsorShip.get().getUuid().toString());

            return ResponseEntity.created(uriComponents.toUri()).build();

        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
