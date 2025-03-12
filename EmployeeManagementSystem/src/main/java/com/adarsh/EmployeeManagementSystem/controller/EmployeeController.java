package com.adarsh.EmployeeManagementSystem.controller;

import com.adarsh.EmployeeManagementSystem.dto.Employee;
import com.adarsh.EmployeeManagementSystem.service.EmployeeService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
      
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/all")
     public List<Employee> getAllEmployee() {
    return employeeService.getAllEmployee(); 
    }

    @PostMapping("/save")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee( @RequestBody Employee employee) {
        return employeeService.updateEmployee( employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}

