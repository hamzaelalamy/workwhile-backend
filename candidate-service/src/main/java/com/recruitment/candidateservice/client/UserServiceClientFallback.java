package com.recruitment.candidateservice.client;

import com.recruitment.candidateservice.to.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClientFallback implements UserServiceClient {

    @Override
    public ResponseEntity<UserDTO> getUserById(String authorizationHeader, String id) {
        // Return minimal user information or null in case of failure
        return ResponseEntity.ok(UserDTO.builder()
                .id(id)
                .firstName("Unavailable")
                .lastName("Unavailable")
                .email("service.unavailable@example.com")
                .active(true)
                .build());
    }
}