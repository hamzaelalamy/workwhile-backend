package com.recruitment.jobservice.service.client;

import com.recruitment.jobservice.to.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClientFallback implements UserServiceClient {

    @Override
    public ResponseEntity<UserDTO> getUserById(String authorizationHeader, String id) {
        // Basic fallback implementation - return empty user
        return ResponseEntity.ok(UserDTO.builder().build());
    }
}