package com.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableEurekaServer
public class AdminPanelAyurvedaMartApplication  {
	

	public static void main(String[] args) {
		SpringApplication.run(AdminPanelAyurvedaMartApplication.class, args);
	}



}
