package com.adarsh.EmployeeManagementSystem.service;

import com.adarsh.EmployeeManagementSystem.dao.EmployeeDao;
import com.adarsh.EmployeeManagementSystem.dto.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EmployeeService {

    @Autowired
    private EmployeeDao dao;

    public Optional<Employee> getEmployeeById(Long id) {
        return dao.getEmployeeById(id);

    }

    public Employee createEmployee(Employee employee) {
    
        return dao.createEmployee(employee);
    }

    public Employee updateEmployee( Employee employee) {
        return dao.updateEmployee(employee);
    }
    public void deleteEmployee(Long id) {
        dao.deleteEmployee(id);
    }

 public List<Employee> getAllEmployee() {
return dao.getAllEmployee(); 
}


}
