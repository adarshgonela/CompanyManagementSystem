package com.adarsh.projectmanagementsystem.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adarsh.projectmanagementsystem.Dto.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,Integer>{

}
