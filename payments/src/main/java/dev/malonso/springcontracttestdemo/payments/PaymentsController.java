package dev.malonso.springcontracttestdemo.payments;

import dev.malonso.springcontracttestdemo.payments.dto.Payment;
import dev.malonso.springcontracttestdemo.payments.dto.PaymentCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    protected PaymentsService paymentsService;

    @Autowired
    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @PostMapping({"","/"})
    public ResponseEntity postPayment(UriComponentsBuilder uriComponentsBuilder,
                                               @Valid @RequestBody PaymentCreate paymentCreate) {
        Payment payment = paymentsService.addPayment(paymentCreate);
        UriComponents uriComponents =
                uriComponentsBuilder.path("/payments/{id}").buildAndExpand(payment.getUuid().toString());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping({"","/"})
    public ResponseEntity<List<Payment>> getPayments() {
        return new ResponseEntity<List<Payment>>(this.paymentsService.getPayments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable String id) {
        return this.paymentsService.getPaymentById(id)
                .map(p -> ResponseEntity.ok(p)).orElse(ResponseEntity.notFound().build());
    }
}
