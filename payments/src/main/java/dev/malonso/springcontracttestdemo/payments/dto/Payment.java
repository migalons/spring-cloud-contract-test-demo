package dev.malonso.springcontracttestdemo.payments.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter @NoArgsConstructor
public class Payment extends PaymentCreate {
    protected UUID uuid;

    public Payment(PaymentCreate paymentCreate) {
        this.uuid = UUID.randomUUID();
        this.holder = paymentCreate.holder;
        this.number = paymentCreate.number;
        this.cvc = paymentCreate.cvc;
        this.expiration = paymentCreate.expiration;
        this.amount = paymentCreate.amount;
    }
}
