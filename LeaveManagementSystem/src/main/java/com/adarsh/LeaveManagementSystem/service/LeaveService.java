package com.adarsh.LeaveManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.LeaveManagementSystem.dao.LeaveDao;
import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;

@Service
public class LeaveService {
    @Autowired
    private LeaveDao dao;

     public LeaveRequest requestleaveOrsaveleave(LeaveRequest leaverequest){
        return dao.requestleaveOrsaveleave(leaverequest);
    }

    public List<LeaveRequest> getallleave(){
        return dao.getallleave();
    }

    public LeaveRequest updateleave(LeaveRequest leaverequest){
        return dao.updateLeave(leaverequest);
    }

    public Optional<LeaveRequest> findByEmployee(Long employeeId){
        return  dao.findByEmployee(employeeId);
    }

    public LeaveRequest updatestatus(Long id, LeaveRequest leaveRequest) {
         return dao.updatestatus(id,leaveRequest);
    }
}
