package com.recruitment.userservice.logic.api;

import com.recruitment.userservice.to.AuthDTOs.AuthResponse;
import com.recruitment.userservice.to.AuthDTOs.LoginRequest;
import com.recruitment.userservice.to.AuthDTOs.PasswordChangeRequest;
import com.recruitment.userservice.to.AuthDTOs.RegisterRequest;
import com.recruitment.userservice.to.UserDTO;

import java.util.List;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserDTO getUserById(String id);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(String id, UserDTO userDTO);

    void changePassword(String userId, PasswordChangeRequest request);

    void activateUser(String id);

    void deactivateUser(String id);

    void deleteUser(String id);
}