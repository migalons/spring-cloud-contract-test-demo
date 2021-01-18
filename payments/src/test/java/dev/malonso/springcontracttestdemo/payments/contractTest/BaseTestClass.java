package dev.malonso.springcontracttestdemo.payments.contractTest;

import dev.malonso.springcontracttestdemo.payments.PaymentsController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseTestClass {

    @Autowired
    private PaymentsController paymentsController;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(paymentsController);
    }

    public void isLocalDateTime(String localDateTime) {
        boolean parseAble = false;
        try {
            LocalDateTime.parse(localDateTime);
            parseAble = true;
        } catch (DateTimeParseException e) {
        }
        assert(parseAble);
    }
}