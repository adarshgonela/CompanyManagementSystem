package com.adarsh.EmployeeManagementSystem.controller;

import com.adarsh.EmployeeManagementSystem.dto.Employee;
import com.adarsh.EmployeeManagementSystem.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ✅ Get Employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with id: {}", id);

        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Employee not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }
    // @GetMapping("/all")
    // public ResponseEntity<List<Employee>> getAllEmployees() {
    // List<Employee> employees = employeeService.getAllEmployee();
    // if (employees.isEmpty()) {
    // return ResponseEntity.noContent().build();
    // }
    // return ResponseEntity.ok(employees);
    // }

    @GetMapping
    public List<Employee> getAllEmployees(int pageSize, int pageNumber) {
        List<Employee> employees = employeeService.getAllEmployee(pageSize, pageNumber);
        if (employees.isEmpty()) {
            return Collections.emptyList();
        }
        return employees;
    }

    // ✅ Create Employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        log.info("Creating employee: {}", employee);

        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        log.info("Updating employee with id: {}", id);

        Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            log.warn("Employee not found for update: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with id: {}", id);

        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.noContent().build();
        }

        log.warn("Employee not found for deletion: {}", id);
        return ResponseEntity.notFound().build();
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

    @GetMapping("/position/{position}")
    public List<Employee> findByPositionEmployees(@PathVariable String position,
            @RequestParam(defaultValue = "0") Long lastId, int pageSize) {
        return employeeService.findByEmployeePosition(position, lastId, pageSize);
    }

    // ✅ Filter by Department (Cursor Pagination)
    @GetMapping("/department/{department}")
    public ResponseEntity<?> findByDepartment(
            @PathVariable String department,
            @RequestParam(defaultValue = "0") Long lastId,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching employees by department: {}", department);

        return ResponseEntity.ok(
                employeeService.findByDepartment(department, lastId, size));
    }
}
