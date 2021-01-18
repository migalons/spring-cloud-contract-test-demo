package dev.malonso.springcontracttestdemo.payments.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data @NoArgsConstructor
public class PaymentCreate {
    @NonNull @NotNull
    protected String holder;
    @NonNull @NotNull
    protected String number;
    @NonNull @NotNull @Pattern(regexp = "^[0-9]{3}$")
    protected String cvc;
    @NonNull @NotNull @Pattern(regexp = "^[0-9]{2}/[0-9]{2}$")
    protected String expiration;
    @NonNull @NotNull
    protected double amount;
    protected String note;
}
