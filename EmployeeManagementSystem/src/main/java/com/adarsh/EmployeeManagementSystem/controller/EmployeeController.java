package com.adarsh.EmployeeManagementSystem.controller;

import com.adarsh.EmployeeManagementSystem.dto.Employee;
import com.adarsh.EmployeeManagementSystem.service.EmployeeService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @GetMapping("/all")
    // public ResponseEntity<List<Employee>> getAllEmployees() {
    // List<Employee> employees = employeeService.getAllEmployee();
    // if (employees.isEmpty()) {
    // return ResponseEntity.noContent().build();
    // }
    // return ResponseEntity.ok(employees);
    // }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployee();
        if (employees.isEmpty()) {
            return Collections.emptyList();
        }
        return employees;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(employee, id);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getPaginatedEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 1 || size < 1) {
            return ResponseEntity.badRequest().build();
        }

        List<Employee> employees = employeeService.getEmployees(page, size);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(employees);
    }

    // @PostMapping("/{id}/upload-profile-pic")
    // public ResponseEntity<String> uploadProfilePic(@PathVariable Long id,
    // @RequestParam("file") MultipartFile file) throws IOException {
    // return employeeService.uploadProfilePic(id, file);
    // }
    // @GetMapping("/{id}/profile-pic")
    // public ResponseEntity<byte[]> getProfilePic(@PathVariable Long id) {
    // return employeeService.getProfilePic(id);
    // }

    // @PutMapping("/upload")
    // public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile
    // file) throws IOException {
    // return employeeService.uploadImage(file);
    // }

    // @GetMapping(path = {"/get/{file}"})
    // public Employee getImage(@PathVariable("file") String fileName) throws
    // IOException {
    // return employeeService.getImage(fileName);
    // }

    @PutMapping("/change-profile/{id}")
    public Optional<Employee> changeprofile(@RequestBody Employee employee, @PathVariable Long id) {
      System.out.println(employee.toString());
        return employeeService.changeGender(employee, id);
    }
}
