package dev.malonso.springcontracttestdemo.events.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ticket {

    protected UUID uuid;
    protected String holder;
    protected LocalDateTime timestamp;
    @Setter protected String paymentId;

    public Ticket(TicketCreate ticketCreate) {
        this.uuid = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
        this.holder = ticketCreate.holder;
    }
}
