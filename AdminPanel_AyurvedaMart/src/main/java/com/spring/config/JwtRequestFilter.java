package com.spring.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.authentication.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the token is present and if it starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        // Validate the token and set authentication in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            Claims claims = jwtUtil.extractClaims(jwt);

         // Safely extract roles and check for null
         Object rolesObject = claims.get("roles");
         List<String> roles = null;

         if (rolesObject instanceof List<?>) {
             roles = (List<String>) rolesObject;
         }

         if (roles == null || roles.isEmpty()) {
             // If roles are missing or empty, handle the case gracefully (log, send error, etc.)
             response.setStatus(HttpServletResponse.SC_FORBIDDEN);
             response.getWriter().write("Roles are missing in the token");
             return;
         }

         // Now you can safely process the roles
         List<SimpleGrantedAuthority> authorities = roles.stream()
                 .map(role -> new SimpleGrantedAuthority(role.startsWith("ROLE_") ? role : "ROLE_" + role))
                 .collect(Collectors.toList());

         // If token is valid, set the authentication
         if (jwtUtil.validateToken(jwt, userDetails)) {
             UsernamePasswordAuthenticationToken authenticationToken = 
                 new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         } else {
             // If the JWT is invalid
             response.setStatus(HttpServletResponse.SC_FORBIDDEN);
             response.getWriter().write("Invalid JWT token");
             return;
         }
        }

        chain.doFilter(request, response);  // Continue the filter chain if token is valid
    }

}
