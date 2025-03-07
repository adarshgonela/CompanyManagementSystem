package com.adarsh.LeaveManagementSystem.controller;

import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import com.adarsh.LeaveManagementSystem.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leavetype")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;
    @PostMapping
    public LeaveType createLeaveType(@RequestBody LeaveType leaveType) {
        return leaveTypeService.saveleavetypeservice(leaveType);
    }

}
