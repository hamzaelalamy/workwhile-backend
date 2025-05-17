package com.recruitment.applicationservice.service;

import com.recruitment.applicationservice.to.UserDTO;

public interface UserService {
    UserDTO getUserByEmail(String email);
}