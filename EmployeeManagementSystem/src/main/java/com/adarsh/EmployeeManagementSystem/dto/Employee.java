package com.adarsh.EmployeeManagementSystem.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Employee ID is required")
    private Long empid;
     @NotBlank(message = "First name is required" )
     @NotNull
    @Size(min = 2, max = 100, message = "First name must be between 2 and 50 characters")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 50 characters")
    @NotNull
    private String lastName;
    // private String email;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number should be 10-15 digits")
    private String phone;
    @NotBlank(message = "Department is required")
   
    private String department;
    @NotBlank(message = "position is required")
   
    private String position;
    private LocalDate hireDate;
    // private String roletype;//employee/hr/manager

}
