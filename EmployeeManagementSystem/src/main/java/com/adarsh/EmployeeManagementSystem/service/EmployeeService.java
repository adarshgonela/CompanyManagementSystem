package com.adarsh.EmployeeManagementSystem.service;

import com.adarsh.EmployeeManagementSystem.dao.EmployeeDao;
import com.adarsh.EmployeeManagementSystem.dto.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service

public class EmployeeService {

    @Autowired
    private EmployeeDao dao;
    private static final String CACHE_NAME = "employees";

    @Cacheable(value = CACHE_NAME, key = "#id")
    public Optional<Employee> getEmployeeById(Long id) {
        Optional<Employee> employee = dao.getEmployeeById(id);
        employee.ifPresentOrElse(
                emp -> System.out.println("Employee found: " + emp),
                () -> System.err.println("Employee with ID " + id + " not found"));
        return employee;
    }

    @CachePut(value = CACHE_NAME, key = "#employee.id")
    public Employee createEmployee(Employee employee) {
        Employee createdEmployee = dao.createEmployee(employee);
        System.out.println("Employee created: " + createdEmployee);
        return createdEmployee;
    }

    @CachePut(value = CACHE_NAME, key = "#empid")
    public Employee updateEmployee(Employee employee, Long empid) {
        Optional<Employee> existingEmployee = dao.getEmployeeById(empid);
        if (existingEmployee.isPresent()) {
            Employee updatedEmployee = dao.updateEmployee(employee, empid);
            System.out.println("Employee updated: " + updatedEmployee);
            return updatedEmployee;
        } else {
            System.err.println("Employee with ID " + empid + " not found for update.");
            return null; // or throw an exception
        }
    }

    @CacheEvict(value = CACHE_NAME, key = "#id")
    public void deleteEmployee(Long id) {
        dao.deleteEmployee(id);
        System.out.println("Employee with ID " + id + " has been deleted.");
    }

    @Cacheable(value = CACHE_NAME, key = "'allEmployees'")
    public List<Employee> getAllEmployee() {
        List<Employee> employees = dao.getAllEmployee();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("All employees retrieved: " + employees);
        }
        return employees;
    }
    
    @Cacheable(value = CACHE_NAME, key = "'employeesPage_' + #pageNumber + '_' + #pageSize")
public List<Employee> getEmployees(int pageNumber, int pageSize) {
    List<Employee> employees = dao.getEmployees(pageNumber, pageSize);

    if (employees.isEmpty()) {
        System.out.println("No employees found for page " + pageNumber);
    } else {
        System.out.println("Employees on page " + pageNumber + ": " + employees);
    }

    return employees;
}


}
