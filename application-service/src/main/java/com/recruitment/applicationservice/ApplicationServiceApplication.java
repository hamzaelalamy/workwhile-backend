package com.recruitment.applicationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient  // Uncomment this when ready to use Eureka
public class ApplicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationServiceApplication.class, args);
    }
}