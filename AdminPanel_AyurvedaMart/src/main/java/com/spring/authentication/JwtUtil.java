package com.spring.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private String SECRET_KEY = "your_secret_key";  // Use a strong secret key, ideally from environment variables
    private final long jwtExpirationMs = 86400000; // 1 day in milliseconds

    // Generate token with username and roles
    public String generateToken(UserDetails userDetails) {
    	  Map<String, Object> claims = new HashMap<>();
    	    claims.put("roles", userDetails.getAuthorities().stream()
    	            .map(GrantedAuthority::getAuthority)
    	            .collect(Collectors.toList()));

    	    return Jwts.builder()
    	            .setClaims(claims)
    	            .setSubject(userDetails.getUsername())
    	            .setIssuedAt(new Date(System.currentTimeMillis()))
    	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
    	            .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Make sure secretKey is your own secret
    	            .compact();
    }

    // Extract all claims from the token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract the username (subject) from the token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Check if the token has expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Validate token by checking username and expiration
    public boolean validateToken(String token, UserDetails userDetails) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
