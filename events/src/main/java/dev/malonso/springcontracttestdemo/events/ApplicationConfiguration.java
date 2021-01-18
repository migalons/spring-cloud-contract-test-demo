package dev.malonso.springcontracttestdemo.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {


    @Value("${service.payments.protocol:http}")
    protected String protocol;
    @Value("${service.payments.host}")
    protected String host;
    @Value("${service.payments.port}")
    protected String port;

    private static Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public EventService getEventService() {
        return new EventService();
    }

    @Bean
    public PaymentsClient getPaymentsClient() {
        return new PaymentsClient(protocol, host, port);
    }
}
