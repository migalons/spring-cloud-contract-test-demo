package dev.malonso.springcontracttestlab.sponsors.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor
public class Event {

    protected UUID uuid;
    protected String name;
    protected String location;
    protected LocalDate date;
    protected double price;
    protected List<SponsorType> sponsorTypes;

}
