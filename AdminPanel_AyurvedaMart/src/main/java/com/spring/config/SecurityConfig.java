package com.spring.config;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring.config.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .requestMatchers("/register").permitAll() // Allow access to authentication routes
            .requestMatchers("/login").permitAll() // Allow access to authentication routes
            .requestMatchers("/api/user/role").permitAll() // Allow access to authentication routes
            .requestMatchers("/api/detail-values").permitAll() // Allow access to authentication routes
            .requestMatchers("/api/product-images/**").permitAll() // Allow access to authentication routes
            .requestMatchers("/admin/product-details/**").permitAll() // Allow access to authentication routes
            .requestMatchers("/api/category-data/**").permitAll() // Allow access to authentication routes
            .requestMatchers("/admin/categories/**").permitAll() // Allow access to authentication routes
//            .requestMatchers("/admin/**").hasRole("ADMIN") // Only allow access to admin routes for ADMIN role
            .requestMatchers("/api/details/**").permitAll() // Only allow access to admin routes for ADMIN role
            .requestMatchers("/admin/products/**").permitAll() // Only allow access to admin routes for ADMIN role
            .requestMatchers("/products/**").permitAll() // Allow public access to product routes
            .requestMatchers("/orders/**").permitAll() // Allow public access to order routes
            .requestMatchers("/dashboard/**").permitAll() // Allow public access to order routes
            .anyRequest().authenticated() // Require authentication for any other requests
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session management (for JWT)
            .and()
            .exceptionHandling().accessDeniedPage("/403"); // Custom access denied page if needed

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Using BCrypt for password encoding
    }

    // CORS Configuration (optional, if you are using a frontend hosted on a different domain)
    @Configuration
    public static class CorsConfig {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override 
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Allow requests from React app
//                        .allowedOrigins("http://192.168.1.15:3000") // Allow requests from React app
//                        .allowedOrigins("http://192.168.54.26:3000") // Allow requests from React app
//                        .allowedOrigins("http://192.168.78.26:3000") // Allow requests from React app
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow common methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials (cookies, authorization headers)
                }
            };
        }
    }
    
    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        return authorities -> authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().replace("ROLE_", "")))
                .collect(Collectors.toList());
    }
}
