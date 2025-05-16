package com.recruitment.applicationservice.service.impl;

import com.recruitment.applicationservice.feign.UserServiceClient;
import com.recruitment.applicationservice.to.UserDTO;
import com.recruitment.applicationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserServiceClient userServiceClient;

    @Override
    public UserDTO getUserByEmail(String email) {
        try {
            return userServiceClient.getUserByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
}