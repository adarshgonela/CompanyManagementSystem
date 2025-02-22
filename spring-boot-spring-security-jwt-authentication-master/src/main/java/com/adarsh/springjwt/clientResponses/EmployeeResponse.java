package com.adarsh.springjwt.clientResponses;

import java.time.LocalDate;

import lombok.Data;
@Data
public class EmployeeResponse {
    
    private Long id;
    private String firstName;
    private String lastName;
    // private String email;
    private String phone;
    private String department;
    private String position;
    private LocalDate hireDate;
    // private String roletype;//employee/hr/manager
}
