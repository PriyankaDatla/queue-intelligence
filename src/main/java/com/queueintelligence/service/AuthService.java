package com.queueintelligence.service;

import com.queueintelligence.dto.RegisterRequest;
import com.queueintelligence.entity.User;
import com.queueintelligence.entity.enums.Role;
import com.queueintelligence.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.queueintelligence.dto.LoginRequest;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String register(RegisterRequest request){

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
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

        if(!user.getPassword()
                .equals(request.getPassword())){

            return "Invalid Password";
        }

        return "Login Successful";
    }
}

