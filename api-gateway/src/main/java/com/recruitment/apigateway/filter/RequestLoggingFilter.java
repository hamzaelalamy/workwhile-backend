package com.recruitment.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestId = UUID.randomUUID().toString();
        
        // Log request details
        logger.info("Request: [{}] {} {} from {}", 
                requestId,
                request.getMethod(),
                request.getURI(),
                request.getRemoteAddress().getAddress().getHostAddress());

        // Add request ID to response headers for tracing
        exchange.getResponse().getHeaders().add("X-Request-ID", requestId);
        
        // Record request start time
        long startTime = System.currentTimeMillis();
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Log response details
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Response: [{}] with status {} in {} ms", 
                    requestId,
                    exchange.getResponse().getStatusCode(),
                    duration);
        }));
    }

    @Override
    public int getOrder() {
        // Set to run before the JWT filter
        return -2;
    }
}
