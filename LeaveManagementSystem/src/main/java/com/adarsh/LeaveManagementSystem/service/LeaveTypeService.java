package com.adarsh.LeaveManagementSystem.service;

import com.adarsh.LeaveManagementSystem.dao.LeaveTypedao;
import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypedao leaveTypedao;

    public LeaveType saveleavetypeservice(LeaveType leaveType){
        return leaveTypedao.saveLeaveType(leaveType);
    }

}
