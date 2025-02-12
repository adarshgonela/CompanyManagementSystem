package com.adarsh.EmployeeManagementSystem.service;

import com.adarsh.EmployeeManagementSystem.dao.EmployeeDao;
import com.adarsh.EmployeeManagementSystem.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EmployeeService {

    @Autowired
    private EmployeeDao dao;

    public Employee getEmployeeById(Long id) {
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




}
