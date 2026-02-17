package com.adarsh.EmployeeManagementSystem.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.adarsh.EmployeeManagementSystem.dto.Employee;

public interface EmployeeServiceInter {
Optional<Employee> getEmployeeById(Long id);
Employee createEmployee(Employee employee);
Employee updateEmployee(Employee employee, Long empid);
boolean deleteEmployee(Long id);
 List<Employee> getAllEmployee(int pageSize,int pageNumber);
List<Employee> getEmployees(int pageNumber, int pageSize);
ResponseEntity<String> uploadImage( MultipartFile file) throws IOException;
Employee getImage( String imageName) throws IOException;
 Optional<Employee> changeGender(Employee e,Long id);
 List<Employee> findByDepartment(String department,Long lastId,int pageSize);
 List<Employee> findByEmployeePosition(String position,Long lastId,int pageSize);

}
