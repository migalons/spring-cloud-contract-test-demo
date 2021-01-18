package dev.malonso.springcontracttestdemo.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse extends PaymentRequest {

    protected UUID uuid;
    private LocalDateTime timestamp;
}
