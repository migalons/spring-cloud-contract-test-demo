package dev.malonso.springcontracttestlab.sponsors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value("${service.payments.protocol}")
    protected String paymentsServiceProtocol;
    @Value("${service.payments.host}")
    protected String paymentsServiceHost;
    @Value("${service.payments.port}")
    protected String paymentsServicePort;
    @Value("${service.events.protocol}")
    protected String eventsServiceProtocol;
    @Value("${service.events.host}")
    protected String eventsServiceHost;
    @Value("${service.events.port}")
    protected String eventsServicePort;

    @Bean(name = "PaymentsClient")
    public PaymentsClient getPaymentsClient() {
        return new PaymentsClient(paymentsServiceProtocol, paymentsServiceHost, paymentsServicePort);
    }

    @Bean(name = "EventsClient")
    public EventsClient getEventsWebClient() {
        return new EventsClient(eventsServiceProtocol, eventsServiceHost, eventsServicePort);
    }

    @Bean
    public SponsorService getSponsorService() {
        return new SponsorService();
    }
}
