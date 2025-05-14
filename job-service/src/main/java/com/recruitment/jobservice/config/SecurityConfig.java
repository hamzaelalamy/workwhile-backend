package com.recruitment.jobservice.config;

import com.recruitment.jobservice.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Error and diagnostics endpoints
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/test/**").permitAll()

                        // Public Job endpoints - anyone can view jobs
                        .requestMatchers(HttpMethod.GET, "/v1/jobs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/jobs/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/jobs/featured").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/jobs/recent").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/jobs/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/jobs/recruiter/{recruiterId}").permitAll()

                        // Secured endpoints - only admin and recruiter can post/edit/delete
                        .requestMatchers(HttpMethod.POST, "/v1/jobs/create_offer").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/v1/jobs/{id}").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.DELETE, "/v1/jobs/{id}").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.PATCH, "/v1/jobs/{id}/activate").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.PATCH, "/v1/jobs/{id}/deactivate").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.POST, "/v1/jobs/{id}/application").authenticated()

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}