package com.recruitment.jobservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // Special admin token for testing in Postman
    private static final String SPECIAL_ADMIN_TOKEN = "ADMIN_TEST_TOKEN_123456789";
    private static final String ADMIN_ID = "admin-user-id-123";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // No auth header, continue with unauthenticated request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        // Special admin token check
        if (SPECIAL_ADMIN_TOKEN.equals(token)) {
            logger.info("Using special admin token for testing");
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    ADMIN_ID,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
            return;
        }

        // Regular JWT token processing
        try {
            final String userId = jwtService.extractUserId(token);
            String role = jwtService.extractRole(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.debug("Set authentication for user: {}", userId);
            }
        } catch (Exception e) {
            logger.error("JWT Authentication failed: {}", e.getMessage());
            // Continue with unauthenticated request
        }

        filterChain.doFilter(request, response);
    }
}