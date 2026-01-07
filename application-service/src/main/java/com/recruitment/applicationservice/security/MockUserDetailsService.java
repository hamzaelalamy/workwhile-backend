package com.recruitment.applicationservice.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MockUserDetailsService implements UserDetailsService {

    public MockUserDetailsService() {
        // Empty constructor
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Always return a mock user with ROLE_USER, as we're bypassing real authentication
        return new User(
                username,
                "password", // Dummy password, not used
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}