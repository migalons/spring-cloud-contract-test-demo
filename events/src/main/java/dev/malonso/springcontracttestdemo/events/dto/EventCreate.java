package dev.malonso.springcontracttestdemo.events.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class EventCreate {
    @NonNull
    protected String name;
    protected String location;
    protected LocalDate date;
    protected double price;
    protected List<SponsorType> sponsorTypes;
}
