package dev.malonso.springcontracttestdemo.events.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event extends EventCreate {

    @Getter
    protected UUID uuid;
    protected List<Ticket> tickets;

    public Event(EventCreate event) {
        this.uuid = UUID.randomUUID();
        this.name = event.name;
        this.location = event.location;
        this.date = event.date;
        this.price = event.price;
        this.sponsorTypes = event.sponsorTypes;
        this.tickets = new ArrayList<Ticket>();
    }

    public Event(String name, String location, LocalDate date, double price, List<SponsorType> sponsorTypes) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.date = date;
        this.price = price;
        this.sponsorTypes = sponsorTypes;
        this.tickets = new ArrayList<Ticket>();
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

}
