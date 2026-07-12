package com.ayush.smart_splitwise.auth.service;

import com.ayush.smart_splitwise.auth.dto.LoginRequest;
import com.ayush.smart_splitwise.auth.dto.RegisterRequest;
import com.ayush.smart_splitwise.auth.dto.RegisterResponse;
import com.ayush.smart_splitwise.common.exception.custom.EmailAlreadyExistsException;
import com.ayush.smart_splitwise.common.exception.custom.PasswordMismatchException;
import com.ayush.smart_splitwise.core.user.entity.User;
import com.ayush.smart_splitwise.core.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest registerRequest) {
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords and confirm password don't match");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        String encodePassword=passwordEncoder.encode(registerRequest.getPassword());
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(encodePassword)
                .phoneNumber(registerRequest.getPhoneNumber())
                .emailVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        User savedUser =userRepository.save(user);
        return new RegisterResponse(savedUser.getId(),"Registered Succesfully");
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        return "Login Succesfully";
    }
}
