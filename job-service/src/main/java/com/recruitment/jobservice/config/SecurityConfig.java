package com.recruitment.jobservice.config;

import com.recruitment.jobservice.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // GET all jobs - accessible by USER and ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/jobs").hasAnyRole("CANDIDATE", "ADMIN", "RECRUITER")

                        // GET job by ID - accessible by USER, ADMIN, and RECRUITER (controller will check if it's their offer)
                        .requestMatchers(HttpMethod.GET, "/api/jobs/{id}").hasAnyRole("CANDIDATE", "ADMIN", "RECRUITER")

                        // POST new job - only RECRUITER and ADMIN can create jobs
                        .requestMatchers(HttpMethod.POST, "/api/jobs").hasAnyRole("RECRUITER", "ADMIN")

                        // PUT (update) job - only RECRUITER (for their own jobs) and ADMIN
                        .requestMatchers(HttpMethod.PUT, "/api/jobs/{id}").hasAnyRole("RECRUITER", "ADMIN")

                        // DELETE job - only RECRUITER (for their own jobs) and ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/jobs/{id}").hasAnyRole("RECRUITER", "ADMIN")

                        // PATCH endpoints for activate/deactivate - RECRUITER and ADMIN only
                        .requestMatchers(HttpMethod.PATCH, "/api/jobs/{id}/**").hasAnyRole("RECRUITER", "ADMIN")

                        // Search endpoints - accessible to all authenticated users
                        .requestMatchers("/api/jobs/search/**").authenticated()

                        // Other job-related operations may need specific permissions
                        .requestMatchers("/api/jobs/featured/**").authenticated()
                        .requestMatchers("/api/jobs/recent/**").authenticated()

                        // Catch-all - require authentication for any other endpoint
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}