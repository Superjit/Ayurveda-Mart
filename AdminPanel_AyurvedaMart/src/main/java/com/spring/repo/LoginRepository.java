package com.spring.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Login;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByUsername(String username);
    Optional<Login> findByEmail(String email);
}
