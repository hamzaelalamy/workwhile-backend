package com.recruitment.applicationservice.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of UserDetailsService for development/testing purposes.
 * In production, this would connect to a real user database.
 */
@Service
public class MockUserDetailsService implements UserDetailsService {

    // Mock user store for testing
    private final Map<String, UserDetails> users = new HashMap<>();

    public MockUserDetailsService() {
        // Initialize with some test users

        // Regular user
        users.put("user-123", new User(
                "user-123",
                "{noop}password",  // {noop} tells Spring Security to not apply any encoding
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        ));

        // Recruiter
        users.put("recruiter-123", new User(
                "recruiter-123",
                "{noop}password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_RECRUITER"))
        ));

        // Admin
        users.put("admin-123", new User(
                "admin-123",
                "{noop}password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // For testing: If the user ID isn't in our mock store but follows a certain pattern,
        // create a user with a role based on the ID prefix
        if (!users.containsKey(userId)) {
            if (userId.startsWith("admin-")) {
                return new User(userId, "{noop}password",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            } else if (userId.startsWith("recruiter-")) {
                return new User(userId, "{noop}password",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_RECRUITER")));
            } else if (userId != null && !userId.isEmpty()) {
                return new User(userId, "{noop}password",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            }

            throw new UsernameNotFoundException("User not found with id: " + userId);
        }

        return users.get(userId);
    }
}