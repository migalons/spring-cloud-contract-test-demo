package dev.malonso.springcontracttestdemo.events.contract;

import dev.malonso.springcontracttestdemo.events.PaymentsClient;
import dev.malonso.springcontracttestdemo.events.dto.PaymentRequest;
import dev.malonso.springcontracttestdemo.events.dto.PaymentResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(
        ids = "dev.malonso.spring-contract-test-demo:payments:1.0.0-SNAPSHOT:stubs:8100",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class PaymentClientTest {


    @Autowired
    PaymentsClient paymentsClient;

    @Test
    public void get_person_from_service_contract() {

        PaymentRequest paymentRequest = new PaymentRequest(
                "Holder Name",
                "1234123412341234",
                "123",
                "01/12",
                1200
        );

        ResponseEntity<PaymentResponse> response = this.paymentsClient.postPayment(paymentRequest);

        assert (response.getStatusCode().equals(HttpStatus.CREATED));
        assert (isUrl(response.getHeaders().getLocation().toString()));

    }

    protected boolean isUrl(String url) {
        try {
            URL u = new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}