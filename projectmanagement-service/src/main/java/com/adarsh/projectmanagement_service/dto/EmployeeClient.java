package com.adarsh.projectmanagement_service.dto;

import lombok.Data;


@Data
public class EmployeeClient {
    private Long empid;
    private String firstName;
    private String lastName;
    // private String email;
    private String phone;
    private String department;
    private String position;
}
