package com.adarsh.LeaveManagementSystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;
import com.adarsh.LeaveManagementSystem.repo.LeaveRepo;

@Repository
public class LeaveDao {
    
    @Autowired
    private LeaveRepo leaveRepo;

    public LeaveRequest requestleaveOrsaveleave(LeaveRequest leaverequest){
        return leaveRepo.save(leaverequest);
    }

    public List<LeaveRequest> getallleave(){
        return leaveRepo.findAll();
    }

    public LeaveRequest updateleave(LeaveRequest leaverequest){
        
       Optional<LeaveRequest> optional= leaveRepo.findById(leaverequest.getLeaveRequestId());
        if (optional.isPresent()) {
            return leaveRepo.save(leaverequest);
        }
        return leaveRepo.save(leaverequest);
    }

}
