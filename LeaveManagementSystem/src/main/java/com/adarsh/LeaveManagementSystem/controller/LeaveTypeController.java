package com.adarsh.LeaveManagementSystem.controller;

import com.adarsh.LeaveManagementSystem.config.LeaveConfig;
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

    @Autowired
private LeaveConfig leaveConfig;

    @PostMapping("/save")
    public LeaveType createLeaveType(@RequestBody LeaveType leaveType) {
     
    leaveType.setVacationCount(leaveConfig.getVacationCount());
    leaveType.setPersonalCount(leaveConfig.getPersonalCount());
    leaveType.setRemoteWorkCount(leaveConfig.getRemoteWorkCount());
    leaveType.setSickLeaveCount(leaveConfig.getSickLeaveCount());
    leaveType.setUnpaidCount(leaveConfig.getUnpaidCount());
        return leaveTypeService.saveleavetypeservice(leaveType);
    }

}
