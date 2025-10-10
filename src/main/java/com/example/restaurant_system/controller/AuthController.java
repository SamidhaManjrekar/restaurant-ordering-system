package com.example.restaurant_system.controller;

import com.example.restaurant_system.dto.JwtResponse;
import com.example.restaurant_system.dto.LoginRequest;
import com.example.restaurant_system.dto.RegisterRequest;
import com.example.restaurant_system.entity.User;
import com.example.restaurant_system.repository.UserRepository;
import com.example.restaurant_system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = authService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully!");
    }

}