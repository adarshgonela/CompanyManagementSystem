package com.adarsh.EmployeeManagementSystem.Repo;

import com.adarsh.EmployeeManagementSystem.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Employee findByEmail(String email);  // Custom query to find employee by email
}
