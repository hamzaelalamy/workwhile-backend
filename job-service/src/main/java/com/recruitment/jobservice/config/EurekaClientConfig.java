package com.recruitment.userservice.config;

import org.springframework.cloud.netflix.eureka.http.WebClientTransportClientFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Supplier;

@Configuration
public class EurekaClientConfig {

    @Bean
    public WebClientTransportClientFactories transportClientFactories() {
        return new WebClientTransportClientFactories(webClientBuilderSupplier());
    }

    @Bean
    public Supplier<WebClient.Builder> webClientBuilderSupplier() {
        return WebClient::builder;
    }
}