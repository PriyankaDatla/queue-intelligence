package com.queueintelligence.service;

import com.queueintelligence.dto.RegisterRequest;
import com.queueintelligence.entity.User;
import com.queueintelligence.entity.enums.Role;
import com.queueintelligence.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.queueintelligence.dto.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import com.queueintelligence.security.JwtService;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request){

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public String login(LoginRequest request){

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if(user == null){
            return "User not found";
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return "Invalid Password";
        }

        return jwtService.generateToken(
                user.getEmail()
        );
    }
}

