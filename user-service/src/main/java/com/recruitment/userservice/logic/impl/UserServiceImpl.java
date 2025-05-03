package com.recruitment.userservice.logic.impl;

import com.recruitment.userservice.dataaccess.dao.UserRepository;
import com.recruitment.userservice.dataaccess.entities.UserEntity;
import com.recruitment.userservice.logic.api.UserService;
import com.recruitment.userservice.security.JwtService;
import com.recruitment.userservice.service.rest.exception.BadRequestException;
import com.recruitment.userservice.service.rest.exception.ResourceNotFoundException;
import com.recruitment.userservice.to.AuthDTOs;
import com.recruitment.userservice.to.AuthDTOs.AuthResponse;
import com.recruitment.userservice.to.AuthDTOs.LoginRequest;
import com.recruitment.userservice.to.AuthDTOs.PasswordChangeRequest;
import com.recruitment.userservice.to.AuthDTOs.RegisterRequest;
import com.recruitment.userservice.to.UserDTO;
import com.recruitment.userservice.to.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .active(true)
                .build();

        UserEntity savedUser = userRepository.save(user);

        return getAuthResponse(savedUser);
    }

    private AuthResponse getAuthResponse(UserEntity savedUser) {
        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        savedUser.getEmail(),
                        savedUser.getPassword(),
                        List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + savedUser.getRole().name()))
                )
        );

        return AuthResponse.builder()
                .token(token)
                .userId(savedUser.getId())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            UserEntity user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return getAuthResponse(user);
        } else {
            throw new BadRequestException("Invalid credentials");
        }
    }

    @Override
    public UserDTO getUserById(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.userEntityToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.userEntityToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userEntityToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userMapper.updateUserEntityFromDTO(userDTO, existingUser);
        UserEntity updatedUser = userRepository.save(existingUser);

        return userMapper.userEntityToUserDTO(updatedUser);
    }

    @Override
    public void changePassword(String userId, PasswordChangeRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void activateUser(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}