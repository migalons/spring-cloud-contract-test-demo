package dev.malonso.springcontracttestdemo.events.contract;

import dev.malonso.springcontracttestdemo.events.EventController;
import dev.malonso.springcontracttestdemo.events.EventService;
import dev.malonso.springcontracttestdemo.events.dto.Event;
import dev.malonso.springcontracttestdemo.events.dto.SponsorType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseTestClass {

    @Autowired
    private EventController eventController;

    @MockBean
    private EventService eventService;

    @Before
    public void setup() {
        Event event = new Event("Commit Conf", "Madrid", LocalDate.parse("2021-03-17"),
                200.00, List.of(new SponsorType("Gold", 500.00),
                new SponsorType("Platinum", 1000.00)));
//        when(eventService.addEvent(any(EventCreate.class))).thenReturn(event);
        when(eventService.getEventByUid(any(String.class))).thenReturn(Optional.of(event));
        RestAssuredMockMvc.standaloneSetup(eventController);
    }
}