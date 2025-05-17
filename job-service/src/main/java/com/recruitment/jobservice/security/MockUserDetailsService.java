package com.recruitment.jobservice.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MockUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // This is a dummy implementation since we're using JWT tokens
        // In a real application, you'd fetch the user from your database or user service
        return new User(
                userId,
                "", // No password needed with JWT
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}