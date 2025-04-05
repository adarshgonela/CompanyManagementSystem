package com.adarsh.projectmanagement_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjectmanagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectmanagementServiceApplication.class, args);
	}

}
