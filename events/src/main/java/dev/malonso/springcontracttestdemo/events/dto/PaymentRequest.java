package dev.malonso.springcontracttestdemo.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PaymentRequest {
    protected String holder;
    protected String number;
    protected String cvc;
    protected String expiration;
    protected double amount;
}
