package com.spring.controller;



import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.spring.authentication.JwtUtil;
import com.spring.entity.Login;
import com.spring.service.LoginService;

@RestController
public class AuthController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(LoginService loginService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Login user) {
    	System.out.println(user);
//    	user.setRole("ADMIN");
    	user.setRole("USER");
        Login registeredUser = loginService.registerUser(user);
        return ResponseEntity.ok("User registered successfully with username: " + registeredUser.getUsername());
    }

    

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Login loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            Optional<Login> optionalUser = loginService.findByEmail(loginRequest.getEmail());
            if (optionalUser.isPresent()) {
                Login user = optionalUser.get(); // Get the actual user entity
                String role = user.getRole(); // Assuming role is a field in the Login entity
                // Update lastLogin timestamp
                user.setLastLogin(LocalDateTime.now());
                loginService.updateUser(user); // Save changes in the database
            // Update lastLogin
            Map<String, String> response = new HashMap<>();
            response.put("jwt", token);
            response.put("username", user.getUsername()); // Return the username
            response.put("role", role); 
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
    }
    }
    
    
    @GetMapping("/api/user/role")
    public String getUserRole() {
        // Get the current authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetails.getAuthorities().toArray()[0].toString(); // Assuming the role is the first authority
        
        return role; // Return the role (e.g., ADMIN, USER)
    }


}
