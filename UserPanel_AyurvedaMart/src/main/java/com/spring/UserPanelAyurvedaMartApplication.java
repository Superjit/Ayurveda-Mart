package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaServer
@ComponentScan(basePackages = {"com.spring"}) 
public class UserPanelAyurvedaMartApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserPanelAyurvedaMartApplication.class, args);
	}

}
