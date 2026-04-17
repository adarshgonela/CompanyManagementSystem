package com.adarsh.EmployeeManagementSystem.service;

import com.adarsh.EmployeeManagementSystem.dao.EmployeeDao;
import com.adarsh.EmployeeManagementSystem.dto.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service

public class EmployeeService implements EmployeeServiceInter {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private static final String CACHE_NAME = "employees";

    private final EmployeeDao dao;

    // @Autowired
    public EmployeeService(EmployeeDao dao) {
        this.dao = dao;
    }

    @Cacheable(value = CACHE_NAME, key = "#id")
    public Optional<Employee> getEmployeeById(Long id) {
        Optional<Employee> employee = dao.getEmployeeById(id);

        if (employee.isPresent()) {
            logger.info("Employee found: {}", employee.get());
        } else {
            logger.warn("Employee with ID {} not found", id);
        }

        return employee;
    }

    @CachePut(value = CACHE_NAME, key = "#employee.id")
    public Employee createEmployee(Employee employee) {
        Employee created = dao.createEmployee(employee);
        logger.info("Employee created: {}", created);
        return created;
    }

    @CachePut(value = CACHE_NAME, key = "#empid")
    public Employee updateEmployee(Employee employee, Long empid) {
        Optional<Employee> existing = dao.getEmployeeById(empid);
        if (existing.isEmpty()) {
            logger.warn("Cannot update: Employee with ID {} not found", empid);
            return null; // Consider throwing custom NotFoundException
        }

        Employee updated = dao.updateEmployee(employee, empid);
        logger.info("Employee updated: {}", updated);
        return updated;
    }

    @CacheEvict(value = CACHE_NAME, key = "#id")
    public boolean deleteEmployee(Long id) {
        Optional<Employee> existing = dao.getEmployeeById(id);
        if (existing.isEmpty()) {
            logger.warn("Cannot delete: Employee with ID {} not found", id);
            return false;
        }

        dao.deleteEmployee(id);
        logger.info("Employee with ID {} deleted successfully", id);
        return true;
    }

    @Cacheable(value = "allemployees", key = "'allEmployees-page-' + #pageNumber + '-size-' + #pageSize")
    public List<Employee> getAllEmployee(int pageSize, int pageNumber) {
        List<Employee> employees = dao.getAllEmployee(pageSize, pageNumber);

        if (employees.isEmpty()) {
            logger.info("No employees found.");
        } else {
            logger.info("Employees retrieved. Page: {}, Size: {}, Count: {}",
                    pageNumber, pageSize, employees.size());
        }
        return employees;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "getemployees", key = "'employeesPage_' + #pageNumber + '_' + #pageSize")
    public List<Employee> getEmployees(int pageNumber, int pageSize) {
        List<Employee> employees = dao.getEmployees(pageNumber, pageSize);

        if (employees.isEmpty()) {
            logger.info("No employees found for page {}", pageNumber);
        } else {
            logger.info("Retrieved {} employees on page {}", employees.size(), pageNumber);
        }

        return employees;
    }

    // public ResponseEntity<String> uploadProfilePic( Long id, MultipartFile file)
    // throws IOException {
    // return dao.uploadProfilePic(id, file);}
    // public ResponseEntity<byte[]> getProfilePic( Long id) {
    // return dao.getProfilePic(id);
    // }

    public ResponseEntity<String> uploadImage(MultipartFile file) throws IOException {
        return dao.uploadImage(file);
    }

    public Employee getImage(String imageName) throws IOException {
        return dao.getImage(imageName);
    }

    public Optional<Employee> changeGender(Employee e, Long id) {
        return dao.changeGender(e, id);
    }

    @Cacheable(value = "employeesByDepartment", key = "#department + '-' + #lastId + '-' + #pageSize")
    public List<Employee> findByDepartment(String department, Long lastId, int pageSize) {
        return dao.findByDepartment(department, lastId, pageSize);
    }

    @Cacheable(value = "employeesByPosition", key = "#position + '-' + #lastId + '-' + #pageSize")
    public List<Employee> findByEmployeePosition(String position, Long lastId, int pageSize) {
        return dao.findByEmployeePosition(position, lastId, pageSize);
    }

}
