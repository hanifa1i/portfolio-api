package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.auth.LoginRequest;
import com.hanif.portfolioapi.dto.auth.LoginResponse;
import com.hanif.portfolioapi.model.User;
import com.hanif.portfolioapi.repository.UserRepository;
import com.hanif.portfolioapi.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Wrong password");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        LoginResponse loginResponse = new LoginResponse();
        String token = jwtTokenUtil.generateToken(user.getUsername());
        loginResponse.setToken(token);

        return loginResponse;
    }
}
