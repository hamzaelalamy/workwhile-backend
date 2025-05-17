package com.recruitment.applicationservice.feign;

import com.recruitment.applicationservice.to.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable String id);

    @GetMapping("/api/users/email")
    UserDTO getUserByEmail(@RequestParam String email);
}