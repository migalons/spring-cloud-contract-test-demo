package dev.malonso.springcontracttestlab.sponsors;

import dev.malonso.springcontracttestlab.sponsors.dto.PaymentRequest;
import dev.malonso.springcontracttestlab.sponsors.dto.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class PaymentsClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private WebClient webClient;

    public PaymentsClient(String protocol, String host, String port) {
        this.webClient = WebClient.builder().baseUrl(protocol + "://" + host + ":" + port).build();
    }

    public ResponseEntity<PaymentResponse> postPayment(PaymentRequest paymentRequest)
            throws WebClientResponseException {
        logger.debug("Send POST /payments");

        return webClient.post().uri("/payments")
                .body(Mono.just(paymentRequest), PaymentRequest.class)
                .retrieve()
                .toEntity(PaymentResponse.class)
                .block();
    }
}
