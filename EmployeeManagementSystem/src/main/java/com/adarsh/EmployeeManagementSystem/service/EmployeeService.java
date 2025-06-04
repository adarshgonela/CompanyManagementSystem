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
        () -> System.err.println("Employee with ID " + id + " not found")
    );
    return employee;
}

 @CachePut(value = CACHE_NAME, key = "#employee.id")
    public Employee createEmployee(Employee employee) {

        return dao.createEmployee(employee);
    }
@CachePut(value = CACHE_NAME, key = "#empid")
    public Employee updateEmployee(Employee employee, Long empid) {
        return dao.updateEmployee(employee, empid);
    }
@CacheEvict(value = CACHE_NAME, key = "#id")
    public void deleteEmployee(Long id) {
        dao.deleteEmployee(id);
    }
@Cacheable(value = CACHE_NAME, key = "'allEmployees'")
    public List<Employee> getAllEmployee() {
        return dao.getAllEmployee();
    }

}
