package com.adarsh.EmployeeManagementSystem.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    public Employee(Long employeeId, String string, String string2, String string3) {
        //TODO Auto-generated constructor stub
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empid;
    private String firstName;
    private String lastName;
    // private String email;
    private String phone;
    private String department;
    private String position;
    private LocalDate hireDate;
    // private String roletype;//employee/hr/manager
    
}
