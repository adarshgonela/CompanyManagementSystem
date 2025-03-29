package com.adarsh.LeaveManagementSystem.controller;

import com.adarsh.LeaveManagementSystem.config.LeaveConfig;
import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import com.adarsh.LeaveManagementSystem.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    leaveType.setPaidCount(leaveConfig.getPaidCount());
        return leaveTypeService.saveleavetypeservice(leaveType);
    }

    @GetMapping("/{empid}")
    public Optional<LeaveType> getleavetypebyempid( @PathVariable Long empid){

                Optional<LeaveType> o=leaveTypeService.getleavetypebyid(empid);
        if (!o.isPresent()) {
            throw new NoSuchElementException("empid not found " + empid);
        }
                return Optional.of(o.get());
    }

    @PatchMapping("/update/{empid}")
    public LeaveType updateLeaveType( @PathVariable Long empid,@RequestBody LeaveType leaveType) {
    return  leaveTypeService.updateleavesbyempid(empid,leaveType);
    }

}
