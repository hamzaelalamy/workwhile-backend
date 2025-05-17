package com.recruitment.applicationservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for Eureka client.
 * Only active when the application is not running with the 'local' profile.
 */
@Configuration
@EnableDiscoveryClient
@Profile("!local")
public class EurekaConfig {
}