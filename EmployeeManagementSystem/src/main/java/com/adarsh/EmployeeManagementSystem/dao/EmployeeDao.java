package com.adarsh.EmployeeManagementSystem.dao;

import com.adarsh.EmployeeManagementSystem.Repo.EmployeeRepository;
import com.adarsh.EmployeeManagementSystem.dto.Employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee( Employee employee) {
            return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

}
