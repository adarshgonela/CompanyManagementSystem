package com.adarsh.EmployeeManagementSystem.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Employee ID is required")
    private Long empid;
    // @NotBlank(message = "First name is required")
    // @Column(name = "last_name", length = 100)
    // @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;
    // @NotBlank(message = "Last name is required")
    // @Size(min = 2, max = 100, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    // private String email;
    // @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number should be 10-15
    // digits")
    private String phone;
    // @NotBlank(message = "Department is required")

    private String department;
    // @NotNull(message = "position is required")
    private String position;
    private LocalDate hireDate;
    // @NotBlank(message = "Gender is required")
    private String gender;

    // @NotBlank(message = "Address is required")
    private String address;
    // private String roletype;//employee/hr/manager

    // private String profile; // URL or path to the profile picture

    @Lob
    @Column(name = "profile_pic", columnDefinition = "LONGBLOB")
    private byte[] profilePic;

    public Employee(String originalFilename, String contentType, byte[] bytes) {
    }

    public Employee(String lastName2, byte[] decompressBytes) {
        // TODO Auto-generated constructor stub
    }

}
