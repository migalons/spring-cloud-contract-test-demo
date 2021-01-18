package dev.malonso.springcontracttestdemo.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public PaymentsService getPaymentsService() {
        return new PaymentsService();
    }

}
