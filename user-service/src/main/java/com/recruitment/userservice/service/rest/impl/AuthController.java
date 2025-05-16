package com.recruitment.userservice.service.rest.impl;

import com.recruitment.userservice.logic.api.UserService;
import com.recruitment.userservice.to.AuthDTOs.AuthResponse;
import com.recruitment.userservice.to.AuthDTOs.LoginRequest;
import com.recruitment.userservice.to.AuthDTOs.PasswordChangeRequest;
import com.recruitment.userservice.to.AuthDTOs.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            // Log the incoming request
            logger.info("Registration request received: {}", registerRequest);

            // Process registration
            AuthResponseDTO response = authService.register(registerRequest);

            // Return response
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Registration error: {}", e.getMessage(), e);
            HashMap<Object, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("error", e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody PasswordChangeRequest request) {

        // In a real implementation, you would extract user ID from the token
        // For simplicity, we're assuming a service would handle this
        // The actual userId would be extracted from the JWT token
        String userId = "user-id-from-token";

        userService.changePassword(userId, request);
        return ResponseEntity.ok().build();
    }
}