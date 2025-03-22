package com.spring.service;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.entity.Login;
import com.spring.repo.LoginRepository;

import java.util.Optional;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // Register a new user
    public Login registerUser(Login user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return loginRepository.save(user);
    }

    // Find user by email
    public Optional<Login> findByEmail(String email) {
        return loginRepository.findByEmail(email);
    }

    // Update user details (e.g., lastLogin, role)
    public void updateUser(Login user) {
        loginRepository.save(user); // Save the updated user in the database
    }
   
}

