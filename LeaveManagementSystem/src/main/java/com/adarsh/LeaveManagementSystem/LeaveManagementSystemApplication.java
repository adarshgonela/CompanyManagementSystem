package com.adarsh.LeaveManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.adarsh.LeaveManagementSystem.dto.LeaveType;

@SpringBootApplication
@EnableFeignClients
public class LeaveManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveManagementSystemApplication.class, args);
		LeaveType c=new LeaveType();
	System.out.println(c.getVacationCount()+" i am vacationcount");
	}

}
