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
           Optional<Employee> optional= employeeFeignController.getEmployeeByIdpost(leaverequest.getEmployeeid());
           
        return service.requestleaveOrsaveleave(leaverequest);
           
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
