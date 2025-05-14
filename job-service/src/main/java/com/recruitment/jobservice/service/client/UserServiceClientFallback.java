package com.recruitment.jobservice.service.client;

import com.recruitment.jobservice.to.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClientFallback implements UserServiceClient {

    @Override
    public ResponseEntity<UserDTO> getUserById(String authorizationHeader, String id) {
        // Implement fallback logic - return minimal user information or null
        UserDTO fallbackUser = new UserDTO();
        fallbackUser.setId(id);
        fallbackUser.setEmail("unavailable@example.com");
        fallbackUser.setFirstName("User Service Unavailable");
        return ResponseEntity.ok(fallbackUser);
    }
}