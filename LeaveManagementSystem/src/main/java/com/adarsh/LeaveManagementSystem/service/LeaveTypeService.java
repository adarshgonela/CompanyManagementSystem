package com.adarsh.LeaveManagementSystem.service;

import com.adarsh.LeaveManagementSystem.dao.LeaveTypedao;
import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypedao leaveTypedao;

    public LeaveType saveleavetypeservice(LeaveType leaveType){
        return leaveTypedao.saveLeaveType(leaveType);
    }
    public Optional<LeaveType> getleavetypebyid(@PathVariable Long empid){
        return  leaveTypedao.getleavetypebyid(empid);
    }
    public LeaveType updateleavesbyempid(Long empid,LeaveType leaveType) {
    return  leaveTypedao.updateleavesbyempid(empid , leaveType);
    }
}
