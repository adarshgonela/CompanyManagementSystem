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

    public LeaveType updateleavesbyempid(LeaveType leaveType) {
        Optional<LeaveType> empId1 = leaveTypeRepo.getLeaveTypeByEmpId(leaveType.getEmpid());
        if (empId1.isPresent()) {
        return leaveTypeRepo.save(leaveType);
        }
        return leaveType;
    }
}
