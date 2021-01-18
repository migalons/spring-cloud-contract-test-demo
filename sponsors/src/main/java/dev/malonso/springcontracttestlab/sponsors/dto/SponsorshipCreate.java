package dev.malonso.springcontracttestlab.sponsors.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @NoArgsConstructor
public class SponsorshipCreate {
    @NonNull
    protected String eventId;
    @NonNull
    protected String type;
    @NonNull
    protected PaymentDetails paymentDetails;
    protected String note;
}
