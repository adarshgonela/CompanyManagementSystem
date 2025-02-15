package com.adarsh.EmployeeManagementSystem.dao;

import com.adarsh.EmployeeManagementSystem.Exceptions.EmployeeFound;
import com.adarsh.EmployeeManagementSystem.Repo.EmployeeRepository;
import com.adarsh.EmployeeManagementSystem.dto.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        Employee existingEmployee  = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployee != null) {
            // If an employee exists, throw an exception
            throw new EmployeeFound("Employee with email " + employee.getEmail() + " already exists.");
        }
        // If no existing employee, save the new employee
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

}
