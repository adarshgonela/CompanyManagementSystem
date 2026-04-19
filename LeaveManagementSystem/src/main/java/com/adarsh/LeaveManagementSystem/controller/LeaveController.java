package com.adarsh.LeaveManagementSystem.controller;

import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;
import com.adarsh.LeaveManagementSystem.refDto.Employee;
import com.adarsh.LeaveManagementSystem.service.LeaveService;
import com.adarsh.LeaveManagementSystem.Feign.EmployeeFeignController;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*") // configure properly in prod
public class LeaveController {

    private static final Logger logger = LoggerFactory.getLogger(LeaveController.class);

    private final LeaveService service;
    private final EmployeeFeignController employeeFeign;

    public LeaveController(LeaveService service,
                           EmployeeFeignController employeeFeign) {
        this.service = service;
        this.employeeFeign = employeeFeign;
    }

    // CREATE LEAVE REQUEST
    @PostMapping
    public ResponseEntity<LeaveRequest> createLeave(@Valid @RequestBody LeaveRequest request) {

        validateEmployee(request.getEmployeeid());

        LeaveRequest saved = service.saveLeave(request);

        logger.info("Leave created for employee ID: {}", request.getEmployeeid());

        return ResponseEntity.ok(saved);
    }

    // UPDATE LEAVE
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeave(
            @PathVariable Long id,
            @Valid @RequestBody LeaveRequest request) {

        request.setLeaveRequestId(id);

        LeaveRequest updated = service.updateLeave(request);

        return ResponseEntity.ok(updated);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {

        List<LeaveRequest> leaves = service.getAllLeaves();

        if (leaves.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(leaves);
    }

    // GET BY EMPLOYEE
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<LeaveRequest> getByEmployee(@PathVariable Long employeeId) {

        return service.findByEmployee(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateStatus(
            @PathVariable Long id,
            @RequestBody LeaveRequest request) {

        LeaveRequest updated = service.updateStatus(id, request);

        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {

        service.deleteLeave(id);

        return ResponseEntity.noContent().build();
    }

    // PAGINATION
    @GetMapping("/page")
    public ResponseEntity<List<LeaveRequest>> getLeaves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<LeaveRequest> leaves = service.getPaginatedLeaves(page, size);

        if (leaves.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(leaves);
    }

    // =========================
    // HELPER METHODS
    // =========================

    private void validateEmployee(Long employeeId) {

        Employee employee = employeeFeign
                .getEmployeeByIdpost(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        logger.info("Validated employee: {}", employee.getId());
    }
}