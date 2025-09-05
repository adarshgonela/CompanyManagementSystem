package com.adarsh.EmployeeManagementSystem.dao;

import com.adarsh.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
import com.adarsh.EmployeeManagementSystem.Repo.EmployeeRepository;
import com.adarsh.EmployeeManagementSystem.dto.Employee;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Repository
public class EmployeeDao {
 private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeDao(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        logger.debug("Fetching employee by ID: {}", id);
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        Employee saved = employeeRepository.save(employee);
        logger.info("Employee created with ID: {}", saved.getId());
        return saved;
    }

    public Employee updateEmployee(Employee employee, Long empId) {
        logger.debug("Updating employee with ID: {}", empId);

        Employee existingEmployee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + empId));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setHireDate(employee.getHireDate());

        Employee updated = employeeRepository.save(existingEmployee);
        logger.info("Employee updated with ID: {}", updated.getId());
        return updated;
    }

    public void deleteEmployee(Long id) {
        logger.debug("Deleting employee with ID: {}", id);
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
        logger.info("Employee with ID {} deleted", id);
    }

    public List<Employee> getAllEmployee() {
        logger.debug("Fetching all employees");
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployees(int pageNumber, int pageSize) {
        logger.debug("Fetching employees - Page: {}, Size: {}", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize); // 0-based indexing
        return employeeRepository.findAll(pageable).getContent();
    }
}
