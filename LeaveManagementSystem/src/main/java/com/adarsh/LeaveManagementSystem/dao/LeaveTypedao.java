package com.adarsh.LeaveManagementSystem.dao;

import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import com.adarsh.LeaveManagementSystem.repo.LeaveTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LeaveTypedao {

    @Autowired
    private LeaveTypeRepo leaveTypeRepo;

    public LeaveType saveLeaveType(LeaveType leaveType){
        return leaveTypeRepo.save(leaveType);
    }


}
