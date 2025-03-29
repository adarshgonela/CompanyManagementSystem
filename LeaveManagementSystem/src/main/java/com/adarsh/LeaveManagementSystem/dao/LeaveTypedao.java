package com.adarsh.LeaveManagementSystem.dao;

import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import com.adarsh.LeaveManagementSystem.repo.LeaveTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public class LeaveTypedao {

    @Autowired
    private LeaveTypeRepo leaveTypeRepo;

    public LeaveType saveLeaveType(LeaveType leaveType){
        return leaveTypeRepo.save(leaveType);
    }

    public Optional<LeaveType> getleavetypebyid(@PathVariable Long empid){
        return  leaveTypeRepo.getLeaveTypeByEmpId(empid);
    }

    public LeaveType updateleavesbyempid(Long empid,LeaveType leaveType) {
        Optional<LeaveType> existingLeaveTypeOpt = leaveTypeRepo.getLeaveTypeByEmpId(empid);

    if (existingLeaveTypeOpt.isPresent()) {
        LeaveType existingLeaveType = existingLeaveTypeOpt.get();

        // Update only the fields that are provided in the incoming request
        if (leaveType.getVacationCount() > 0) {
            existingLeaveType.setVacationCount(leaveType.getVacationCount());
        }
        if (leaveType.getSickLeaveCount() > 0) {
            existingLeaveType.setSickLeaveCount(leaveType.getSickLeaveCount());
        }
        if (leaveType.getRemoteWorkCount() > 0) {
            existingLeaveType.setRemoteWorkCount(leaveType.getRemoteWorkCount());
        }
        if (leaveType.getPersonalCount() > 0) {
            existingLeaveType.setPersonalCount(leaveType.getPersonalCount());
        }
        if (leaveType.getPaidCount() > 0) {
            existingLeaveType.setPaidCount(leaveType.getPaidCount());
        }

        // Save the updated LeaveType
        return leaveTypeRepo.save(existingLeaveType);
    }

    // If the employee ID doesn't exist, return a 404 or handle the error as needed
    throw new RuntimeException("LeaveType not found for employee ID " + empid);

    }
}
