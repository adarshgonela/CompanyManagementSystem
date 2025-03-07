package com.adarsh.LeaveManagementSystem.service;

import com.adarsh.LeaveManagementSystem.config.LeaveConfig;
import com.adarsh.LeaveManagementSystem.dao.LeaveTypedao;
import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypedao leaveTypedao;

    public LeaveType saveleavetypeservice(LeaveType leaveType){
//        LeaveConfig leaveConfig=new LeaveConfig();
//
//        leaveType.setSickLeaveCount(leaveConfig.getSickLeaveCount());
//        leaveType.setUnpaidCount(leaveConfig.getUnpaidCount());
//        leaveType.setPersonalCount(leaveConfig.getPersonalCount());
//        leaveType.setRemoteWorkCount(leaveConfig.getRemoteWorkCount());
//        leaveType.setVacationCount(leaveConfig.getVacationCount());

        return leaveTypedao.saveLeaveType(leaveType);
    }

}
