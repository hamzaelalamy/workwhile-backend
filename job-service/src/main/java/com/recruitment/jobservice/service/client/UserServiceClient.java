package com.recruitment.jobservice.service.client;

import com.recruitment.jobservice.to.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "user-service",
        url = "${user-service.url}",
        fallback = UserServiceClientFallback.class
)
public interface UserServiceClient {

    @GetMapping("/api/v1/users/{id}")
    ResponseEntity<UserDTO> getUserById(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("id") String id);
}