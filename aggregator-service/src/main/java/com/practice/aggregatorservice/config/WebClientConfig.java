package com.practice.aggregatorservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("stockService")
    public WebClient webClientStockService() {
        return WebClient.builder().baseUrl("http://localhost:7070").build();
    }
    @Bean("customerService")
    public WebClient webClientCustomerService() {
        return WebClient.builder().baseUrl("http://localhost:6060").build();
    }
}
