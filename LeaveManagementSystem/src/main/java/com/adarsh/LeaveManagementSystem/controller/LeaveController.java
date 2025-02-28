package com.adarsh.LeaveManagementSystem.controller;

import com.adarsh.LeaveManagementSystem.ExceptionHandlers.InvalidLeaveRequestException;
import com.adarsh.LeaveManagementSystem.ExceptionHandlers.LeaveRequestNotFoundException;
import com.adarsh.LeaveManagementSystem.Feign.EmployeeFeignController;
import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;
import com.adarsh.LeaveManagementSystem.refDto.Employee;
import com.adarsh.LeaveManagementSystem.service.LeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/leave")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveController {

    @Autowired
    private EmployeeFeignController employeeFeignController;

    @Autowired
    private LeaveService service;


    @PostMapping("/save")
    public LeaveRequest requestleaveOrsaveleave(@RequestBody LeaveRequest leaverequest) {
        try {
            // Check if employee exists using the employeeFeignController
            Optional<Employee> optional = employeeFeignController.getEmployeeById(leaverequest.getEmployee());

            if (optional.isPresent()) {
                // Employee exists, process leave request or save leave
                return service.requestleaveOrsaveleave(leaverequest);
            } else {
                // Employee not found, throw exception
                throw new RuntimeException("Employee not found with ID: " + leaverequest.getEmployee());
            }

        } catch (InvalidLeaveRequestException ex) {
            // Handle invalid leave request exceptions
            throw new InvalidLeaveRequestException("Invalid leave request: " + ex.getMessage());
        } catch (Exception ex) {
            // Handle other exceptions
            throw new RuntimeException("An error occurred while processing the leave request: " + ex.getMessage());
        }
    }


    @PutMapping("/update")
    public LeaveRequest updateleave(@RequestBody LeaveRequest leaverequest) {
        try {
            return service.updateleave(leaverequest);
        } catch (LeaveRequestNotFoundException ex) {
            throw new LeaveRequestNotFoundException("Leave request not found for update: " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the leave request: " + ex.getMessage());
        }
    }

    @GetMapping("/all")
    public List<LeaveRequest> getallemployees() {
    return   service.getallleave();
    }


    @GetMapping("/leaveid/{employeeId}")
    public  List<LeaveRequest> findByEmployee(@PathVariable Long employeeId){
        return  service.findByEmployee(employeeId);
    }

    @GetMapping("/test")
    public  String findByEmployee1(){
        return  "hi hlo hru";
    }


}
